import numpy as np
from PIL import Image
from scipy.ndimage import zoom, gaussian_filter

def resize_image(image, target_size):
    return image.resize(target_size)

def downsample(image):
    return image[::2, ::2]

def upsample(image):
    if len(image.shape) == 2:
        return gaussian_filter(zoom(image, 2, order=1),sigma=1.0)
    elif len(image.shape) == 3:
        return gaussian_filter(zoom(image, (2, 2, 1), order=1),sigma=1.0)

def gaussPyramid(image, levels):
    imagegp = [image]
    for i in range(levels):
        imagegp.append(downsample(imagegp[i]))
    return imagegp

def laplPyramid(imagegp):
    imagelp = []
    for i in range(len(imagegp) - 1):
        expanded = upsample(imagegp[i + 1])
        imagelp.append(imagegp[i] - expanded)
    imagelp.append(imagegp[len(imagegp) - 1])
    return imagelp

def blend(image1lp, image2lp, maskgp):
  blended = []

  for i in range(len(maskgp)):
    maskgpNegative = abs(1-maskgp[i])
    blended.append(maskgp[i]*image1lp[i] + maskgpNegative*image2lp[i])
  return blended

def collapse(blended):
    collapsed = blended[len(blended)-1]
    for i in range(len(blended)-1, 0, -1):
        upsampled = upsample(collapsed)
        collapsed = (upsampled + blended[i - 1]).astype(np.uint8)
    return collapsed



image1 = Image.open("image1.jpg").convert("RGB")
image2 = Image.open("image2.jpg").convert("RGB")
mask = Image.open("mask.jpg")
target_size = (1024, 1024)
image1 = resize_image(image1, target_size)
image2 = resize_image(image2, target_size)
mask = resize_image(mask, target_size)

image1 = np.array(image1)
image2 = np.array(image2)
mask = np.array(mask)

maskgp = gaussPyramid(mask, 4)
image1gp = gaussPyramid(image1, 4)
image2gp = gaussPyramid(image2, 4)

image1lp = laplPyramid(image1gp)
image2lp = laplPyramid(image2gp)

blended = blend(image1lp,image2lp,maskgp)
collapsed = collapse(blended)
for i in range(4):
    x = Image.fromarray(image1gp[i])
    x.save(f"image_1_gp_{i}.jpg")
    y = Image.fromarray(image2gp[i])
    y.save(f"image_2_gp_{i}.jpg")
    z = Image.fromarray(image1lp[i])
    z.save(f"image_1_lp_{i}.jpg")
    w = Image.fromarray(image2lp[i])
    w.save(f"image_2_lp_{i}.jpg")
    t = Image.fromarray(blended[i])
    t.save(f"blended_{i}.jpg")
final = Image.fromarray(collapsed)
final.save("collapsed.jpg")

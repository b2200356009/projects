from PIL import Image, ImageFilter
import numpy as np

inputimg = "input1.jpg"
smoothedimg = "smoothed.jpg"
edgesimg = "edges.jpg"
quantizedimg = "quantized.jpg"
finalimg = "final.jpg"

#smoothing

image = Image.open(inputimg).convert('RGB')
filterType = "gaussian"
sigma = 1
if filterType == 'gaussian':
    smoothed = image.filter(ImageFilter.GaussianBlur(radius=sigma))
    smoothed.save(smoothedimg)
elif filterType == 'median':
    smoothed = image.filter(ImageFilter.MedianFilter(size=sigma))
    smoothed.save(smoothedimg)

#edge detection
sigma1 = 1.4
sigma2 = 1.6
epsilon = 50
img = Image.open(smoothedimg).convert('L')
blurredImg1 = np.array(img.filter(ImageFilter.GaussianBlur(radius=sigma1)))
blurredImg2 = np.array(img.filter(ImageFilter.GaussianBlur(radius=sigma2)))
dog_image = blurredImg1 - blurredImg2
thresholdedDog = np.where(dog_image >= epsilon, 255, 0).astype('uint8')
Image.fromarray(thresholdedDog, mode='L').save(edgesimg)

#quantization
numColors = 32
image = Image.open(smoothedimg)
#Convert to numpy
imgArray = np.array(image)
#Convert to Lab
labImage = image.convert('LAB')
#Quantize the L
quantizedL = (np.array(labImage.split()[0]) // numColors) * numColors
L, a, b = labImage.split()
quantizedLab = Image.merge('LAB', (Image.fromarray(quantizedL), a, b))
#Convert to RGB
quantizedRGB = quantizedLab.convert('RGB')
quantizedRGB.save(quantizedimg)


#Final process
edgeImage = Image.open(edgesimg)
quantizedImage = Image.open(quantizedimg)
#Convert to numpy
edgeArray = np.array(edgeImage)
quantizedArray = np.array(quantizedImage)
#Convert to grayscale
quantizedLuminance = np.array(quantizedImage.convert('L'))
invertedEdge = 255 - edgeArray
resultArray = quantizedArray * (invertedEdge / 255.0).reshape((*invertedEdge.shape, 1))
resultImage = Image.fromarray(resultArray.astype('uint8'))
resultImage.save(finalimg)

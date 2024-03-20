import cv2
import numpy as np
from matplotlib import pyplot as plt
from skimage.segmentation import slic, mark_boundaries
from skimage.filters import gabor

def extract_rgb_feature(image_path):
    image = cv2.imread(image_path)
    image_rgb = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
    height, width, _ = image_rgb.shape
    rgb_feature = image_rgb.reshape((height * width, 3))
    return rgb_feature

def extract_location_rgb_feature(image_path):
    image = cv2.imread(image_path)
    image_rgb = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
    height, width, _ = image_rgb.shape
    rgb_and_location = np.zeros((height * width, 5), dtype=np.float32)
    for i in range(height):
        for j in range(width):
            pixel_values = image_rgb[i, j]
            rgb_and_location[i * width + j, :3] = pixel_values / 255.0
            rgb_and_location[i * width + j, 3] = i / float(height)
            rgb_and_location[i * width + j, 4] = j / float(width)
    return rgb_and_location

def mean_of_rgb_spx(image_path):
    image = cv2.imread(image_path)
    image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
    segments = slic(image, n_segments=500, compactness=60)
    num_segments = len(np.unique(segments))
    segment_means = np.zeros((num_segments, 3), dtype=np.float32)
    height, width, _ = image.shape
    for segment_index in range(1, num_segments + 1):
        segment_mask = segments == segment_index
        segment_pixels = image[segment_mask]
        segment_means[segment_index - 1] = np.mean(segment_pixels, axis=0)
    segment_colors = segment_means.astype(np.uint8)
    output_image = np.zeros((image.shape[0], image.shape[1], 3), dtype=np.uint8)
    for i in range(image.shape[0]):
        for j in range(image.shape[1]):
            output_image[i, j] = segment_colors[segments[i, j] - 1]
    plt.imshow(output_image)
    plt.show()
    return segment_means, segments

def color_histogram(image_path):
    image = cv2.imread(image_path)
    image_rgb = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
    segments = slic(image_rgb, n_segments=100, compactness=60)
    num_segments = len(np.unique(segments))
    color_histograms = np.zeros((num_segments, 3, 256), dtype=np.int32)
    for segment_index in range(1, num_segments + 1):
        segment_mask = segments == segment_index
        segment_pixels = image_rgb[segment_mask]
        for channel_index in range(3):
            channel_values = segment_pixels[:, channel_index]
            hist, _ = np.histogram(channel_values, bins=range(257))
            color_histograms[segment_index - 1, channel_index] = hist
    return color_histograms

def mean_of_gabor(image_path, ng):
    image = cv2.imread(image_path)
    image_rgb = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
    segments = slic(image_rgb, n_segments=300, compactness=60)
    height, width = image_rgb.shape[:2]
    num_segments = len(np.unique(segments))
    image_r = image_rgb[:, :, 0]
    image_g = image_rgb[:, :, 1]
    image_b = image_rgb[:, :, 2]
    gabor_responses = np.zeros((num_segments, 3))
    gabor_r = np.zeros((height, width, ng))
    gabor_g = np.zeros((height, width, ng))
    gabor_b = np.zeros((height, width, ng))
    for i in range(ng):
        th = 300 * i / ng
        fr = 0.1 + 0.15 * i
        gabor_r[:, :, i] = gabor(image_r, frequency=fr, theta=th)[0]
        gabor_g[:, :, i] = gabor(image_g, frequency=fr, theta=th)[0]
        gabor_b[:, :, i] = gabor(image_b, frequency=fr, theta=th)[0]
    for i in range(1, num_segments + 1):
        mask = (segments == i)
        gabor_responses[i - 1][0] = np.mean(gabor_r[mask], axis=(0, 1))
        gabor_responses[i - 1][1] = np.mean(gabor_g[mask], axis=(0, 1))
        gabor_responses[i - 1][2] = np.mean(gabor_b[mask], axis=(0, 1))
    segment_colors = gabor_responses.astype(np.uint8)
    output_image = np.zeros((image.shape[0], image.shape[1], 3), dtype=np.uint8)
    for i in range(image.shape[0]):
        for j in range(image.shape[1]):
            output_image[i, j] = segment_colors[segments[i, j] - 1]
    plt.imshow(output_image)
    plt.show()
    return gabor_responses, segments

def kmeans_cluster(feature_type):
    image_path = "image.jpg"
    num_clusters = 3
    if feature_type == "gabor":
        gabor_responses, segments = mean_of_gabor(image_path, 10)
        centroids = [[0, 0, 255], [0, 255, 0], [255, 0, 0]]
        clustered = np.zeros((len(gabor_responses)))
        for i in range(len(gabor_responses)):
            distance = ((gabor_responses[i][0] - centroids[0][0]) ** 2 + (gabor_responses[i][1] - centroids[0][1]) ** 2 + (
                    gabor_responses[i][2] - centroids[0][2]) ** 2) ** (1 / 3)
            num_centroid = 0
            for j in range(num_clusters):
                distance_new = ((gabor_responses[i][0] - centroids[j][0]) ** 2 + (gabor_responses[i][1] - centroids[j][1]) ** 2 + (
                        gabor_responses[i][2] - centroids[j][2]) ** 2) ** (1 / 3)
                if distance < distance_new:
                    continue
                else:
                    distance = distance_new
                    num_centroid = j
            clustered[i] = num_centroid
        plot_clusters(image_path, clustered, segments)
    elif feature_type == "meanofRGBspx":
        segment_means, segments = mean_of_rgb_spx(image_path)
        centroids = [[0, 0, 255], [0, 255, 0], [255, 0, 0]]
        clustered = np.zeros((len(segment_means)))
        for i in range(len(segment_means)):
            distance = ((segment_means[i][0] - centroids[0][0]) ** 2 + (segment_means[i][1] - centroids[0][1]) ** 2 + (
                    segment_means[i][2] - centroids[0][2]) ** 2) ** (1 / 3)
            num_centroid = 0
            for j in range(num_clusters):
                distance_new = ((segment_means[i][0] - centroids[j][0]) ** 2 + (segment_means[i][1] - centroids[j][1]) ** 2 + (
                        segment_means[i][2] - centroids[j][2]) ** 2) ** (1 / 3)
                if distance < distance_new:
                    continue
                else:
                    distance = distance_new
                    num_centroid = j
            clustered[i] = num_centroid
        plot_clusters(image_path, clustered, segments)
    elif feature_type == "extractRGBFeature":
        feature = extract_rgb_feature(image_path) / 255.0
        centroids = [[0, 0, 255], [0, 255, 0], [255, 0, 0]]
        clustered = np.zeros((len(feature)))
        for i in range(len(feature)):
            distance = ((feature[i][0] - centroids[0][0]) ** 2 + (feature[i][1] - centroids[0][1]) ** 2 + (
                    feature[i][2] - centroids[0][2]) ** 2) ** (1 / 3)
            num_centroid = 0
            for j in range(num_clusters):
                distance_new = ((feature[i][0] - centroids[j][0]) ** 2 + (feature[i][1] - centroids[j][1]) ** 2 + (
                        feature[i][2] - centroids[j][2]) ** 2) ** (1 / 3)
                if distance < distance_new:
                    continue
                else:
                    distance = distance_new
                    num_centroid = j
            clustered[i] = num_centroid
        height, width, _ = cv2.imread(image_path).shape
        clustered = clustered.reshape((height, width))
        plt.imshow(clustered)
        plt.show()
    elif feature_type == "extractLocationRGBFeature":
        feature = extract_location_rgb_feature(image_path)
        centroids_indices = np.random.choice(len(feature), size=num_clusters, replace=False)
        centroids = feature[centroids_indices]
        clustered = np.zeros((len(feature)))
        for i in range(len(feature)):
            distance = ((feature[i][0] - centroids[0][0]) ** 2 + (feature[i][1] - centroids[0][1]) ** 2 + (
                    feature[i][2] - centroids[0][2]) ** 2 + (feature[i][3] - centroids[0][3]) ** 2 + (
                                feature[i][4] - centroids[0][4]) ** 2) ** (1 / 5)
            num_centroid = 0
            for j in range(num_clusters):
                distance_new = ((feature[i][0] - centroids[j][0]) ** 2 + (feature[i][1] - centroids[j][1]) ** 2 + (
                        feature[i][2] - centroids[j][2]) ** 2 + (feature[i][3] - centroids[j][3]) ** 2 + (
                                        feature[i][4] - centroids[j][4]) ** 2) ** (1 / 5)
                if distance < distance_new:
                    continue
                else:
                    distance = distance_new
                    num_centroid = j
            clustered[i] = num_centroid
        height, width, _ = cv2.imread(image_path).shape
        clustered = clustered.reshape((height, width))
        plt.imshow(clustered)
        plt.show()
    return num_clusters

def plot_clusters(image_path, cluster_labels, segments):
    image = cv2.imread(image_path)
    image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
    cluster_colors = np.random.randint(0, 255, size=(len(np.unique(cluster_labels)), 3), dtype=np.uint8)
    colored_image = np.zeros_like(image)
    for cluster_index in range(len(np.unique(cluster_labels))):
        cluster_mask = np.where(cluster_labels == cluster_index)[0]
        for segment_index in cluster_mask:
            segment_mask = segments == (segment_index + 1)
            colored_image[segment_mask] = cluster_colors[cluster_index]
    plt.imshow(mark_boundaries(colored_image, segments))
    plt.axis('off')
    plt.show()
    plt.imshow(mark_boundaries(image, segments))
    plt.axis('off')
    plt.show()
kmeans_cluster("gabor")
kmeans_cluster("meanofRGBspx")
kmeans_cluster("extractRGBFeature")
kmeans_cluster("extractLocationRGBFeature")

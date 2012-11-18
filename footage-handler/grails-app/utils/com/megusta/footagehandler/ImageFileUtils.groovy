package com.megusta.footagehandler

import java.awt.Graphics2D
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.awt.RenderingHints
import java.awt.AlphaComposite

/**
 * Provides operation with images.
 */
class ImageFileUtils {

    /**
     * Gets picture's width.
     *
     * @param file image file
     * @return image width in pixels
     */
    static int getPictureWidth(File file) {
        BufferedImage img = ImageIO.read(file)
        img.getWidth()
    }

    /**
     * Gets picture height.
     *
     * @param file image file
     * @return image height in pixels
     */
    static int getPictureHeight(File file) {
        BufferedImage img = ImageIO.read(file)
        img.getHeight()
    }

    /**
     * Crops an image.
     *
     * @param file image file
     * @param cropDirection direction to crop
     * @param pixelsToCrop pixels to crop
     */
    static void crop(File file, CropDirection cropDirection, int pixelsToCrop) {
        switch (cropDirection) {
            case CropDirection.RIGHT:
                crop(file, 0, pixelsToCrop, 0, 0)
                break;
            case CropDirection.LEFT:
                crop(file, 0, 0, 0, pixelsToCrop)
                break;
            case CropDirection.TOP:
                crop(file, pixelsToCrop, 0, 0, 0)
                break;
            case CropDirection.BOTTOM:
                crop(file, 0, 0, pixelsToCrop, 0)
                break;
            default:
                //should be never thrown
                throw new IllegalArgumentException("Invalid direction parameter")
        }
    }

    /**
     * Crops an image.
     *
     * @param file image file to crop
     * @param top pixels to crop at the top
     * @param right pixels to crop at the the right side
     * @param bottom pixels to crop at the bottom
     * @param left pixels to crop at the left side
     */
    static void crop(File file, int top, int right, int bottom, int left) {
        BufferedImage img = ImageIO.read(file)
        ImageIO.write(img.getSubimage(left, top, img.getWidth() - left - right, img.getHeight() - top - bottom),
                FileUtils.getFileExtension(file.getName()), file)
    }

    /**
     * Stretches an image.
     *
     * @param file image file to stretch
     * @param width target width
     * @param height target height
     */
    static void stretch(File file, int width, int height) {
        BufferedImage img = ImageIO.read(file)
        BufferedImage stretchedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        Graphics2D graphics2D = stretchedImg.createGraphics()
        graphics2D.setComposite(AlphaComposite.Src);
        graphics2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
                RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
                RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_DITHERING,
                RenderingHints.VALUE_DITHER_DISABLE);
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(img, 0, 0, width, height, null)
        graphics2D.dispose()
        ImageIO.write(stretchedImg, FileUtils.getFileExtension(file.getName()), file)
    }

    /**
     * Stretches an image to the target pixel width or height.
     *
     * @param file image file
     * @param stretchDirection stretch direction
     * @param targetPixels target pixel width or height
     */
    static void stretch(File file, StretchDirection stretchDirection, int targetPixels) {
        BufferedImage img = ImageIO.read(file)
        int targetWidth = stretchDirection.equals(StretchDirection.HORIZONTAL) ? targetPixels : img.getWidth()
        int targetHeight = stretchDirection.equals(StretchDirection.VERTICAL) ? targetPixels : img.getHeight()
        stretch(file, targetWidth, targetHeight)
    }

    /**
     * Crops an image by coordinates, selected by user.
     *
     * @param file image file to crop
     * @param x x coordinate of the top left rectangle apex
     * @param y y coordinate of the top left rectangle apex
     * @param x2 x coordinate of the bottom right rectangle apex
     * @param y2 y coordinate of the bottom right rectangle apex
     * @return true , if image was cropped succesfully, false, if one or more of the rectangle apexes is out of image bounds
     */
    static boolean cropUsersImage(File file, int x, int y, int x2, int y2) {
        boolean result = false
        BufferedImage img = ImageIO.read(file)
        int width = img.getWidth()
        int height = img.getHeight()
        if (x >= 0 && x <= width && x2 >= 0 && x2 <= width && y >= 0 && y <= height && y2 >= 0 && y2 <= height) {
            crop(file, y, img.getWidth() - x2, img.getHeight() - y2, x)
            result = true
        }

        result
    }

    /**
     * Adjusts the picture file.
     *
     * @param file file to adjust
     * @param profile file profile
     */
    static void adjust(File file, ImageFileProfile profile) {
        int curX = ImageFileUtils.getPictureWidth(file)
        int curY = ImageFileUtils.getPictureHeight(file)
        int targetX = profile.targetWidth
        int targetY = profile.targetHeight
        if (targetX - curX >= 0 && targetY - curY >= 0) {
            // scales file to target size, if target width and height are bigger than current ones.
            simplyScaling(file, profile)
        } else if (targetX - curX < 0 && targetY - curY < 0) {
            // scales and crops file to target size, if target width and height are smaller than current ones.
            balancedScalingAndCropping(file, curX, curY, profile)
        } else {
            // If one of the target sizes is bigger than current and the other one is smaller,
            // mixed scaling and cropping will be used.
            // For the bigger size will be used scaling and for the smaller one
            // the unbalanced cropping (when cropping has a priority)
            mixedScalingAndCropping(file, curX, curY, profile)
        }
    }

    /**
     * Adjusts the picture file.
     *
     * @param file file to adjust
     * @param profile file profile
     */
    static void adjust(File file, int targetX, int targetY) {
        int curX = ImageFileUtils.getPictureWidth(file)
        int curY = ImageFileUtils.getPictureHeight(file)

        if (targetX >= curX && targetY >= curY) {
            // scales file to target size, if target width and height are bigger than current ones.
            simplyScaling(file, targetX, targetY)
        } else if (targetX - curX < 0 && targetY - curY < 0) {
            // scales and crops file to target size, if target width and height are smaller than current ones.
            balancedScalingAndCropping(file, targetX, targetY)
        } else {
            // If one of the target sizes is bigger than current and the other one is smaller,
            // mixed scaling and cropping will be used.
            // For the bigger size will be used scaling and for the smaller one
            // the unbalanced cropping (when cropping has a priority)
            mixedScalingAndCropping(file, targetX, targetY)
        }
    }


    /**
     * Scales image file,
     * applicable when both target width and height are bigger than current ones.
     *
     * @param file file to scale
     * @param profile image file profile
     */
    static void simplyScaling(File file, ImageFileProfile profile) {
        ImageFileUtils.stretch(file, profile.targetWidth, profile.targetHeight)
    }

    /**
     * Scales image file,
     * applicable when both target width and height are bigger than current ones.
     *
     * @param file file to scale
     * @param profile image file profile
     */
    static void simplyScaling(File file, int targetWidth, int targetHeight) {
        ImageFileUtils.stretch(file, targetWidth, targetHeight)
    }

    /**
     * Does balanced scaling and cropping,
     * applicable when both target width and height are smaller than current ones.
     *
     * @param file file to adjust
     * @param curX current width
     * @param curY current height
     * @param profile image file profile
     */
    static void balancedScalingAndCropping(File file, int curX, int curY, ImageFileProfile profile) {
        int croppingX = (curX - getBalancedCropping(curX, profile.targetWidth,
                profile.getMaxHorizontalCropping(), profile.getMaxHorizontalScaling())) / 2
        int croppingY = (curY - getBalancedCropping(curY, profile.targetHeight,
                profile.getMaxVerticalCropping(), profile.getMaxVerticalScaling())) / 2
        ImageFileUtils.crop(file, croppingY, croppingX, croppingY, croppingX)
        ImageFileUtils.stretch(file, profile.targetWidth, profile.targetHeight)
    }

    /**
     * Does balanced scaling and cropping,
     * applicable when both target width and height are smaller than current ones.
     *
     * @param file file to adjust
     * @param targetWidth desired width
     * @param targetHeight desired height
     */
    static void balancedScalingAndCropping(File file, int targetWidth, int targetHeight) {
        int curX = ImageFileUtils.getPictureWidth(file)
        int curY = ImageFileUtils.getPictureHeight(file)

        Double xFactor = curX / targetWidth
        Double yFactor = curY / targetHeight

        Double factor = Math.abs(xFactor - yFactor)

        if(factor != 0) {
            if(xFactor <= yFactor) {
                ImageFileUtils.stretch(file, StretchDirection.HORIZONTAL, (targetWidth * (xFactor + (factor / 2))) as int) //Making the minor side bigger
                int croppingArea = curY - (targetHeight * (yFactor - factor))
                ImageFileUtils.crop(file, CropDirection.TOP, (croppingArea / 2) as int)
                ImageFileUtils.crop(file, CropDirection.BOTTOM, (croppingArea / 2) as int)
            } else {
                ImageFileUtils.stretch(file, StretchDirection.VERTICAL, (targetHeight * (yFactor + (factor / 2))) as int) //Making the minor side bigger
                int croppingArea = curX - (targetWidth * (xFactor - factor))
                ImageFileUtils.crop(file, CropDirection.RIGHT, (croppingArea / 2) as int)
                ImageFileUtils.crop(file, CropDirection.LEFT, (croppingArea / 2) as int)
            }
        }

        ImageFileUtils.stretch(file, targetWidth, targetHeight)
    }


    /**
     * Does mixed scaling and cropping,
     * applicable when one of the target sizes is bigger than current and the other one is smaller.
     *
     * @param file file to adjust
     * @param curX current width
     * @param curY current height
     * @param profile image file profile
     */
    static void mixedScalingAndCropping(File file, int curX, int curY, ImageFileProfile profile) {
        if (curX > profile.getTargetWidth()) {
            // applies unbalanced cropping for X size
            int croppingX = (curX - getUnbalancedCropping(curX, profile.targetWidth, profile.getMaxHorizontalCropping(),
                    profile.getMaxHorizontalScaling())) / 2
            ImageFileUtils.crop(file, 0, croppingX, 0, croppingX)
        } else if (curY > profile.targetHeight) {
            // applies unbalanced cropping for Y size
            int croppingY = (curY - getUnbalancedCropping(curY, profile.targetHeight, profile.getMaxVerticalCropping(),
                    profile.getMaxVerticalScaling())) / 2
            ImageFileUtils.crop(file, croppingY, 0, croppingY, 0)
        }
        ImageFileUtils.stretch(file, profile.targetWidth, profile.targetHeight)
    }

    static void mixedScalingAndCropping(File file, int targetWidth, int targetHeight) {
        int curX = ImageFileUtils.getPictureWidth(file)
        int curY = ImageFileUtils.getPictureHeight(file)
        int maxHorizontalCropping = 0
        int maxHorizontalScaling = 0
        int maxVerticalScaling = 0
        int maxVerticalCropping = 0

        if (curX > targetWidth) {
            // applies unbalanced cropping for X size
            int croppingX = (curX - getUnbalancedCropping(curX, targetWidth, maxHorizontalCropping,
                    maxHorizontalScaling)) / 2
            ImageFileUtils.crop(file, 0, croppingX, 0, croppingX)
        } else if (curY > targetHeight) {
            // applies unbalanced cropping for Y size
            int croppingY = (curY - getUnbalancedCropping(curY, targetHeight, maxVerticalCropping,
                    maxVerticalScaling)) / 2
            ImageFileUtils.crop(file, croppingY, 0, croppingY, 0)
        }
        ImageFileUtils.stretch(file, targetWidth, targetHeight)
    }

    /**
     * Gets cropping by balanced cropping and scaling algorithm.
     *
     * @param curSize current size
     * @param targetSize target size
     * @param maxAllowedCropping max allowed cropping
     * @param maxAllowedScaling max allowed scaling
     * @return cropping value
     */
    static int getBalancedCropping(int curSize, int targetSize, int maxAllowedCropping, int maxAllowedScaling) {
        int res
        int proposedSize = curSize - (curSize - targetSize) / 2
        int allowedCropping = getAllowedSize(curSize, maxAllowedCropping)
        int allowedScaling = getAllowedSize(curSize, maxAllowedScaling)
        if (isResizingLimited(proposedSize, allowedCropping)) {
            // if cropping has a limit then take max allowing cropping
            res = allowedCropping
        } else if (isResizingLimited(proposedSize, allowedScaling)) {
            // if scaling has a limit then take max allowing scaling
            res = targetSize + curSize - allowedScaling
        } else {
            // if there is no limit take proposed cropping
            res = proposedSize
        }

        res
    }

    /**
     * Gets cropping by unbalanced cropping and scaling algorithm.
     *
     * @param curSize current size
     * @param targetSize target size
     * @param maxAllowedCropping max allowed cropping
     * @param maxAllowedScaling max allowed scaling
     * @return cropping value
     */
    static int getUnbalancedCropping(int curSize, int targetSize, int maxAllowedCropping, int maxAllowedScaling) {
        int res
        int allowedCropping = getAllowedSize(curSize, maxAllowedCropping)
        if (isResizingLimited(targetSize, allowedCropping)) {
            // if cropping has a limit then take max allowing cropping
            res = allowedCropping
        } else {
            // if there is no limit take proposed cropping
            res = targetSize
        }

        res
    }

    /**
     * Gets min allowed size in pixels.
     *
     * @param curSize current size
     * @param maxAllowedResizing max allowed resizing in %
     * @return max allowed size in pixels
     */
    static int getAllowedSize(int curSize, int maxAllowedResizing) {
        return curSize - maxAllowedResizing * curSize / 100
    }

    /**
     * Checks is resizing limited by allowed size.
     *
     * @param proposedSize proposed size
     * @param allowedSize allowed size
     * @return true if resizing is limited by allowed size, false otherwise
     */
    static boolean isResizingLimited(int proposedSize, int allowedSize) {
        return allowedSize > proposedSize
    }

    /**
     * Verifies is adjusting applicable for a file.
     *
     * @param file file for which adjusting will be verified
     * @param profile image file profile
     * @return true if adjusting applicable, false otherwise
     */
    static boolean isAdjustingApplicable(File file, ImageFileProfile profile) {

        return isAdjustingApplicableForSize(ImageFileUtils.getPictureHeight(file), profile.getTargetHeight(),
                profile.getMaxVerticalScaling(), profile.getMaxVerticalCropping()) &&
                isAdjustingApplicableForSize(ImageFileUtils.getPictureWidth(file), profile.getTargetWidth(),
                        profile.getMaxHorizontalScaling(), profile.getMaxHorizontalCropping())
    }

    /**
     * Verifies is adjusting applicable for a size.
     *
     * @param curSize current size
     * @param targetSize target size
     * @param maxScaling max allowed scaling
     * @param maxCropping max allowed cropping
     * @return true if adjusting is applicable, false otherwise
     */
    static boolean isAdjustingApplicableForSize(int curSize, int targetSize, int maxScaling, int maxCropping) {
        boolean res
        int adjustingPrc = Math.abs(targetSize * 100 / curSize - 100)
        if (curSize > targetSize) {
            res = adjustingPrc <= maxScaling + maxCropping
        } else {
            res = adjustingPrc <= maxScaling
        }

        res
    }
}

/**
 * Enum of directions to crop image.
 */
public enum CropDirection {
    RIGHT,
    LEFT,
    TOP,
    BOTTOM
}

/**
 * Enum of directions to stretch image.
 */
public enum StretchDirection {
    HORIZONTAL,
    VERTICAL
}

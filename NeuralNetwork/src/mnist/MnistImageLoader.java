/*
  	Copyright 2019	Yusa T�rkmen

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	    http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.

 */

package mnist;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class MnistImageLoader {
	
	public static BufferedImage loadImage(String path) throws Exception{
		return resize(ImageIO.read(new File(path)),28,28);
	}

	public static BufferedImage resize(BufferedImage img, int newW, int newH) {
		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		return dimg;
}  

	public static int[][] bufferedImageToArray(BufferedImage img) {
		int[][] arr = new int[img.getWidth()][img.getHeight()];

		for(int i = 0; i < img.getWidth(); i++)
		    for(int j = 0; j < img.getHeight(); j++)
		        arr[i][j] = img.getRGB(i, j);
		
		return arr;
	}

	public static int[][] bufferedImageRedToArray(BufferedImage img) {
		int[][] arr = new int[img.getWidth()][img.getHeight()];
		
		for(int i = 0; i < img.getWidth(); i++)
		    for(int j = 0; j < img.getHeight(); j++)
		        arr[i][j] = new Color(img.getRGB(i, j)).getRed();
		
		return arr;
	}
	
	public static double[] intArrayToDoubleArray(int[][] i) {
		double[] ar = new double[i.length * i[0].length];
		for(int j = 0 ; j < i.length; j ++){
			for(int n = 0; n < i[0].length; n++){
				ar[j * i.length + n] = (double)i[n][j] / (double)256;
			}
		}
		return ar;
	}
	
	public static double[] invert(double[] ar) {
		for(int j = 0 ; j < ar.length; j ++){
			ar[j] = 0.9999-ar[j];
		}
		return ar;
	}
	
}

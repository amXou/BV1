// BV Ue1 WS2021/22 Vorgabe
//
// Copyright (C) 2021 by Klaus Jung
// All rights reserved.
// Date: 2021-07-14
 		   		     	

package bv_ws2122;

public class GaussFilter {
 		   		     	
	private double[][] kernel;
 		   		     	
	public double[][] getKernel() {
		return kernel;
	}

	public void apply(RasterImage src, RasterImage dst, int kernelSize, double sigma) {
 		   		     	
		// TODO: Implement a Gauss filter of size "kernelSize" x "kernelSize" with given "sigma"
		
		// Step 1: Allocate appropriate memory for the field variable "kernel" representing a 2D array.
		kernel = new double[kernelSize][kernelSize];
		
		// Step 2: Fill in appropriate values into the "kernel" array.
		int hotspot = (kernelSize/2);
		double sum = 0;
		for (int x = 0; x < kernelSize; x ++) {
			for (int y = 0; y < kernelSize; y++) {
				double d = (Math.sqrt((Math.pow(Math.abs(y-hotspot), 2) + (Math.pow(Math.abs(y-hotspot), 2)))));
				kernel[x][y] = Math.pow(Math.E, (-Math.pow(d, 2)) / (2 * Math.pow(sigma, 2)));
				sum = sum + kernel[x][y];
			}
		}
		// Hint:
		// Use g(d) = e^(- d^2 / (2 * sigma^2)), where d is the distance of a coefficient's position to the hot spot.
		// Note that in this comment e^ denotes the exponential function and ^2 the square. In Java ^ is a different operator. 
		
		// Step 3: Normalize the "kernel" such that the sum of all its values is one.
		for (int x = 0; x < kernelSize; x ++) {
			for (int y = 0; y < kernelSize; y++) {
				kernel[x][y] = kernel[x][y] / sum;
			}
		}	
		
		// Step 4: Apply the filter given by "kernel" to the source image "src". The result goes to image "dst".
		// Use "constant continuation" for boundary processing.
		
		for(int posx = 0; posx < src.width; posx ++) {
			for(int posy = 0; posy < src.height; posy ++) {
				int pos = posy * src.width + posx;
				int gray; // = src.argb[pos] & 0xff;
				if(!(posx < hotspot && posx > src.width - hotspot 
				   && posy < hotspot && posy > src.height - hotspot)) {
					double newvalue = 0;
					for(int k = - hotspot; k < hotspot; k++) {
						for(int l = - hotspot; l < hotspot; l++) {
							newvalue = newvalue + src.argb[(posx + k) + (posy + l) * src.width] * kernel[k+hotspot][l+hotspot];
						}
					}
					gray = (int) newvalue;
					dst.argb[pos] = 0xff000000 | gray << 16 | gray << 8 | gray;
				} else {
					dst.argb[pos] = 0xff000000; // | r << 16 | g << 8 | b;
				}
			}
		} 
	}
		/*for(int pos = 0; pos < src.argb.length; pos++) {
			int r = src.argb[pos] >> 16 & 0xff;
			int g = src.argb[pos] >> 8 & 0xff;
			int b = src.argb[pos] & 0xff;
			
			
			
			dst.argb[pos] = 0xff000000 | r << 16 | g << 8 | b;
	}
		  */ 		     	

}
 		   		     	





/* 
Part of the ReCode Project (http://recodeproject.com)
Based on "Gaussian Distribution" by Kerry Jones
Originally published in "Computer Graphics and Art" v2n2, 1977
Copyright (c) 2013 Josh Giesbrecht - OSI/MIT license (http://recodeproject/license).
Edited for The 70s Party Project by Giorgos Petkakis, 2017
*/

color fg = color(10,10,10);
color bg = color(255, 0);

void fadeOut() {
  fill(bg);
  
  for (int i=0;  i<4000; i++)  {
    int x = (int)random(-width,width);
    int y = floor((int)(myGaussian() * height*0.50)) + height*4/9;
    noStroke();
    fill(fg);
    ellipse(x, y, 12, 12);
  }
}

// ported from http://www.colingodsey.com/javascript-gaussian-random-number-generator/
float myGaussian() {
  float x1, x2, rad;
 
  do {
    x1 = 2 * random(1) - 1;
    x2 = 2 * random(1) - 1;
    rad = x1 * x1 + x2 * x2;
  } while(rad >= 1 || rad == 0);
 
  float c = sqrt(-2 * (float)Math.log(rad) / rad);
 
  return x1 * c;
};
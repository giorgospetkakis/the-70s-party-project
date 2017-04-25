/* 
Part of the ReCode Project (http://recodeproject.com)
Based on "Random Squares" by Charles Csuri
Originally published in "Computer Graphics and Art" vol1 no2, 1976
Copyright (c) 2012 Chris Allick - OSI/MIT license (http://recodeproject/license).
Edited for The 70s Party Project by Giorgos Petkakis, 2017
*/

// These are the bigger 'foreground' rounded squares
void rects() {
    for (int n = 0; n < fft.specSize(); n++)
  {
    if (fft.getBand(n)>fft.getBand(highest))
      highest=n;
      
          if (highest > highestEver)
      highestEver = highest;
  }
  // Set stroke parameters
  fill(50 * strokeWeightVolume);
  
  // Draw ellipses
  for( int i = 1; i < 80 * strokeWeightVolume; i++ ) {
    strokeWeight(0.2f * strokeWeightVolume);
    ellipse( x(highest+y(highest / i)), y(highest+x(highest / i)), random(4, 8), random(4, 8));
  }
}

float x (float in)
{
  return sin(in)*height/2 + cos(in/20) * height/4 + sin(in/y(in))*height/4;
}

float y (float in)
{
  return sin(in)*height/2 * cos(in/height);
}
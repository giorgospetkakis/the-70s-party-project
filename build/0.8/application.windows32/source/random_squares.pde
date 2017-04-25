/* 
Part of the ReCode Project (http://recodeproject.com)
Based on "Random Squares" by Charles Csuri
Originally published in "Computer Graphics and Art" vol1 no2, 1976
Copyright (c) 2012 Chris Allick - OSI/MIT license (http://recodeproject/license).
Edited for The 70s Party Project by Giorgos Petkakis, 2017
*/

int size = 1;
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
  if(highest % 2 == 0) {
    fill(highest * strokeWeightVolume,  125 * highest/250, 0);
  } else if (highest % 5 == 0 || highest % 7 == 0) {
    fill(125 * highest/250, 0, highest * strokeWeightVolume);
  } else {
    fill(0, 255 * strokeWeightVolume, 255 * highest/50, 255 * strokeWeightVolume);
  }
  
  
  // Draw ellipses
  for( int i = 1; i < 80 * strokeWeightVolume; i++ ) {
    strokeWeight(0.2f * strokeWeightVolume);
    rect( x(highest+y(highest / i)), y(highest+x(highest / i)), random(1 * size, 4 * size), random(2 * size, 15 * size));
  }
  System.out.println(highest);
}

float x (float in)
{
  return sin(in)*height/2 + cos(in/20) * height/4 + sin(in/y(in))*height/4;
}

float y (float in)
{
  return sin(in)*height/2 * cos(in/height);
}
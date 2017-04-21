/* 
Part of the ReCode Project (http://recodeproject.com)
Based on "Random Squares" by Charles Csuri
Originally published in "Computer Graphics and Art" vol1 no2, 1976
Copyright (c) 2012 Chris Allick - OSI/MIT license (http://recodeproject/license).
Edited for The 70s Party Project by Giorgos Petkakis, 2017
*/

// These are the bigger 'foreground' rounded squares
void rects() {
  // Set stroke parameters
  strokeWeight(0.5f * strokeWeightVolume);
  stroke(255 * strokeWeightVolume);
  
  // Draw ellipses
  for( int i = 0; i < 30 * strokeWeightVolume; i++ ) {
    rect( random(-width,width), random(-height,height), random( 2, 5), random(2,5));
    ellipse( random(-width,width), random(-height,height), random( 2, 5), random(2,5));
  }
}
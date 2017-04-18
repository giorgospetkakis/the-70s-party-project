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
  strokeWeight(1.5f * strokeWeightVolume);
  stroke(255 * strokeWeightVolume);
  
  // Draw squares
  for( int i = 0; i < 50 * strokeWeightVolume; i++ ) {
    roundedRect( random(-width,width), random(-height,height), random( 5, 40), random(5,40), 10, 10 );
  }
}

// Code adapted from "cefnhoile" at https://forum.processing.org/topic/rounded-rectangle
void roundedRect(float x, float y, float w, float h, float rx, float ry) {
  beginShape();
  vertex(x, y+ry); //top of left side
  bezierVertex(x, y, x, y, x+rx, y); //top left corner
 
  vertex(x+w-rx, y); //right of top side
  bezierVertex(x+w, y, x+w, y, x+w, y+ry); //top right corner
 
  vertex(x+w, y+h-ry); //bottom of right side
  bezierVertex(x+w, y+h, x+w, y+h, x+w-rx, y+h); //bottom right corner
 
  vertex(x+rx, y+h); //left of bottom side
  bezierVertex(x, y+h, x, y+h, x, y+h-ry); //bottom left corner
 
  endShape(CLOSE);
}
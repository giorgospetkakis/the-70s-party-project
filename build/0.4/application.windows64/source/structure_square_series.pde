import java.util.Collections;

/* 
Part of the ReCode Project (http://recodeproject.com)
Based on "Structure Square Series - Inward" by Roger Coqart
Originally published in "Computer Graphics and Art" vol1 no3, 1976
Copyright (c) 2012 Greg Borenstein - OSI/MIT license (http://recodeproject/license).
Edited for The 70s Party Project by Giorgos Petkakis, 2017
*/

// These are the smaller, background squares

int scale = 10; // Global scaling of the component

int rowSize = 20;
int marginSize = 8 * scale;
int boxSize = 4 * scale;

int rotation = 0;

ArrayList<Integer> strokes;

void init() {
  strokes = new ArrayList<Integer>();
  
  for (int i = 0; i < 8 * scale; i++) {
      strokes.add(i);
  }
}
void boxes() {
  noFill();
   pushMatrix();
     translate(width/2, height/2);
     rotate(PI/100 + ++rotation);
  for (int row = 0; row < rowSize; row++) {
    for (int col = 0; col < rowSize; col++) {
      stroke(random(0, 1) * (255  * strokeWeightVolume), random(0,1) * (255 * strokeWeightVolume));
      strokeWeight(random(0,1) * 0.5 * strokeWeightVolume);
      int x = boxSize*col + marginSize*col;
      int y = boxSize*row + marginSize*row;

      pushMatrix();
      translate(x, y);
      ellipse(random(0,5 * strokeWeightVolume) + boxSize/2, random(0,5 * strokeWeightVolume) + boxSize/2, boxSize, boxSize);
      //rect(random(0,10 * strokeWeightVolume), random(0,10 * strokeWeightVolume), boxSize, boxSize);

      // this is the one clever bit:
      // measure the distance of the square from the 
      // center in concentric rings to find out how many segments to draw
      int middle = rowSize / 2;
      int distFromMiddle = max(abs(row - middle), abs(col-middle));

      // do the strokes in a random different order each time
      Collections.shuffle(strokes);
      for (int i = 0; i < distFromMiddle; i++) {
        drawSegment(strokes.get(i), boxSize, boxSize);
      }
      popMatrix();
    }
  }
     popMatrix();
}


void drawSegment(int i, int w, int h) {
  switch(i) {
  case 0:
    line(0, 0, w, h);
    break;

  case 1:
    line(w, 0, 0, h);
    break;

  case 2:
    line(0, h/2, w, h/2);
    break;

  case 3:
    line(0, h/2, w/2, 0);
    break;

  case 4:
    line(w/2, 0, w, h/2);
    break;

  case 5:
    line(w, h/2, w/2, h);
    break;

  case 6:
    line(w/2, 0, w/2, h);
    break;

  case 7:
    line(w/2, h, 0, h/2);
    break;
  }
}
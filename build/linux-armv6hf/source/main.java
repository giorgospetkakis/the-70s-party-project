import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import ddf.minim.analysis.*; 
import java.util.Collections; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class main extends PApplet {




/*
Created for The 70s Party Project by Giorgos Petkakis, 2017
*/

FFT fft;
Minim minim;
AudioInput in;
BeatDetect beat;

int mode = 1;
float strokeWeightVolume = 1;
int volumeControl = 10;
float rotation_main = 0;

public void setup()
{
  //set up graphics
  //smooth();
  background(100);
  //size(800,800,P3D);
  
  frameRate(80);
  
  //set up sound
  minim = new Minim(this);
  in = minim.getLineIn(Minim.MONO, 4096, 44100);
  fft = new FFT(in.mix.size(), 44100);
  beat = new BeatDetect();
  
  beat.setSensitivity(20);
  beat.detectMode(BeatDetect.SOUND_ENERGY);
  
  init();
}

public void draw()
{
  captureSound();
  strokeWeightVolume = volumeControl * in.mix.level();
  
  
  if (beat.isOnset()) {
    strokeWeightVolume *= 2;
  }
    fadeOut();
    pushMatrix();
      translate(width/2, height/2);
      rotate(rotation_main);
      generate();
    popMatrix();
  
  rotation_main += PI/100;
}

public void captureSound()
{
  fft.forward(in.left);
  beat.detect(in.left);
}

public void generate()
{
  switch (mode)
  {
    case 0:
      clear();
      break;
    case 1:
      rects();
      boxes();
      break;
    default:
      break;
  }
}

public void keyPressed()
{
  switch(keyCode)
  {
    case '0':{
      background(0);
      mode = 0;
      break;
    }
    case '1':{
      mode = 1;
      break;
    }
    case UP:{
      volumeControl += 10;
      break;
    }
    case DOWN:{
      if(volumeControl != 0){
        volumeControl -= 10;
      }
      break;
    }
  }
}
/* 
Part of the ReCode Project (http://recodeproject.com)
Based on "Gaussian Distribution" by Kerry Jones
Originally published in "Computer Graphics and Art" v2n2, 1977
Copyright (c) 2013 Josh Giesbrecht - OSI/MIT license (http://recodeproject/license).
Edited for The 70s Party Project by Giorgos Petkakis, 2017
*/

int fg = color(10,10,10);
int bg = color(255, 0);

public void fadeOut() {
  fill(bg);
  
  for (int i=0;  i<4000; i++)  {
    int x = (int)random(-width,width);
    int y = floor((int)(myGaussian() * height*0.50f)) + height*4/9;
    noStroke();
    fill(fg);
    ellipse(x, y, 12, 12);
  }
}

// ported from http://www.colingodsey.com/javascript-gaussian-random-number-generator/
public float myGaussian() {
  float x1, x2, rad;
 
  do {
    x1 = 2 * random(1) - 1;
    x2 = 2 * random(1) - 1;
    rad = x1 * x1 + x2 * x2;
  } while(rad >= 1 || rad == 0);
 
  float c = sqrt(-2 * (float)Math.log(rad) / rad);
 
  return x1 * c;
};
/* 
Part of the ReCode Project (http://recodeproject.com)
Based on "Random Squares" by Charles Csuri
Originally published in "Computer Graphics and Art" vol1 no2, 1976
Copyright (c) 2012 Chris Allick - OSI/MIT license (http://recodeproject/license).
Edited for The 70s Party Project by Giorgos Petkakis, 2017
*/

// These are the bigger 'foreground' rounded squares
public void rects() {
  // Set stroke parameters
  strokeWeight(1.5f * strokeWeightVolume);
  stroke(255 * strokeWeightVolume);
  
  // Draw squares
  for( int i = 0; i < 50 * strokeWeightVolume; i++ ) {
    roundedRect( random(-width,width), random(-height,height), random( 5, 40), random(5,40), 10, 10 );
  }
}

// Code adapted from "cefnhoile" at https://forum.processing.org/topic/rounded-rectangle
public void roundedRect(float x, float y, float w, float h, float rx, float ry) {
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

public void init() {
  strokes = new ArrayList<Integer>();
  
  for (int i = 0; i < 8 * scale; i++) {
      strokes.add(i);
  }
}
public void boxes() {
   noFill();
   pushMatrix();
     translate(width/2, height/2);
     rotate(PI/100 + ++rotation);
  for (int row = 0; row < rowSize; row++) {
    for (int col = 0; col < rowSize; col++) {
      stroke(random(0, 1) * (255  * strokeWeightVolume));
      strokeWeight(random(0,1) * 0.1f * strokeWeightVolume);
      int x = boxSize*col + marginSize*col;
      int y = boxSize*row + marginSize*row;

      pushMatrix();
      translate(x, y);
      //ellipse(random(0,5 * strokeWeightVolume) + boxSize/2, random(0,5 * strokeWeightVolume) + boxSize/2, boxSize, boxSize);
      rect(random(0,10 * strokeWeightVolume), random(0,10 * strokeWeightVolume), boxSize, boxSize);

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


public void drawSegment(int i, int w, int h) {
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
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#000000", "--hide-stop", "main" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}

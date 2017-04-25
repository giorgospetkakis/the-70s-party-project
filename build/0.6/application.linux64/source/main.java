import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import ddf.minim.analysis.*; 
import processing.opengl.*; 
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
int volumeControl = 100;
float rotation_main = 0;
int highest, highestEver;
boolean modeSwitch = true;

public void setup()
{
  //set up graphics
  background(0);
  
  //fullScreen();
  frameRate(80);
  //set up sound
  minim = new Minim(this);
  in = minim.getLineIn(Minim.MONO, 4096, 44100);
  fft = new FFT(in.mix.size(), 44100);
  beat = new BeatDetect();
  
  beat.setSensitivity(0);
  beat.detectMode(BeatDetect.SOUND_ENERGY);
  
  init();
}

public void draw()
{
  highest = 10;
  captureSound();
  strokeWeightVolume = volumeControl * in.left.level();
    //fadeOut();
    pushMatrix();
      translate(width/2, height/2);
      rotate(rotation_main);
      generate();
    popMatrix();
   
  rotation_main += PI/2*strokeWeightVolume;
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
      fadeOut();
      rects();
      if(beat.isOnset())
        distort(5);
      break;
    case 2:
      boxes();
      distort(10);
      if(beat.isOnset())
        clear();
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
      frameRate(100);
      break;
    }
    case '2':{
      frameRate(25);
      mode = 2;
      break;
    }
    case UP:{
      volumeControl += 2;
      break;
    }
    case DOWN:{
      if(volumeControl != 0){
        volumeControl -= 2;
      }
      break;
    }
    case 4:{
      volumeControl = 0;
      break;
    }
    case 5:{
      volumeControl = 25;
      break;
    }
    case 6:{
      volumeControl = 50;
      break;
    }
    case 7:{
      volumeControl = 75;
      break;
    }
    case ' ':{
      clear();
      break;
    }
  }
}

public void distort(int mult){
    int x1 = (int) random(0,width);
    int y1 = (int) random(0,height);
  
    int x2 = round(x1 + random(-100 * mult * strokeWeightVolume, 100 * mult * strokeWeightVolume));
    int y2 = round(y1 + random(-100 * mult * strokeWeightVolume, 100 * mult * strokeWeightVolume));
  
    int w = (int)(60 * mult * strokeWeightVolume);
    int h = (int)(60 * mult * strokeWeightVolume);
    
    x1 = (int) random(0, width);
    y1 = 0;
  
    x2 = round(x1 + random(-7 * mult * strokeWeightVolume, 7 * mult * strokeWeightVolume));
    y2 = round(random(-5 * mult * strokeWeightVolume, 5 * mult * strokeWeightVolume));
  
    w = (int) random(8 * mult * strokeWeightVolume, 8 * mult * strokeWeightVolume);
    h = (int) random(8 * mult * strokeWeightVolume, 8 * mult * strokeWeightVolume);

    copy(x1,y1, w,h, x2,y2, w,h);
}
/* 
Part of the ReCode Project (http://recodeproject.com)
Based on "Gaussian Distribution" by Kerry Jones
Originally published in "Computer Graphics and Art" v2n2, 1977
Copyright (c) 2013 Josh Giesbrecht - OSI/MIT license (http://recodeproject/license).
Edited for The 70s Party Project by Giorgos Petkakis, 2017
*/

int fg = color(0);
int bg = color(0);

public void fadeOut() {
  fill(bg);
  for (int i=0;  i<1500; i++)  {
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
// P_2_1_2_03.pde
// 
// Generative Gestaltung, ISBN: 978-3-87439-759-9
// First Edition, Hermann Schmidt, Mainz, 2009
// Hartmut Bohnacker, Benedikt Gross, Julia Laub, Claudius Lazzeroni
// Copyright 2009 Hartmut Bohnacker, Benedikt Gross, Julia Laub, Claudius Lazzeroni
//
// http://www.generative-gestaltung.de
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
 


float tileCount = 10;
int moduleColor = color(204);
int moduleAlpha = 100;
int actRandomSeed = 0;
int max_distance = 500; 

public void draw3d() {
  float xMusicFollow = map(strokeWeightVolume, 0f, volumeControl * 1.5f, width/4f, 3*(float)width/4);
  float yMusicFollow = map(strokeWeightVolume, 0f, volumeControl * 1.5f, height/4f, 3*(float)height/4);
  noFill();
  actRandomSeed = (int) random(strokeWeightVolume*100);
  randomSeed(actRandomSeed);

  stroke(moduleColor, moduleAlpha);
  strokeWeight(0.2f * strokeWeightVolume);

  for (int gridY=-width/4; gridY<width * 1.15f; gridY+=width/tileCount) {
    for (int gridX=-height/4; gridX<height * 1.15f; gridX+=height/tileCount) {

      float diameter = dist(xMusicFollow, yMusicFollow, gridX, gridY);
      diameter = diameter/max_distance * 40;
      pushMatrix();
        ellipse(gridX + diameter * 3, gridY + diameter * 3, diameter, diameter);    //// also nice: ellipse(...)
      popMatrix(); 
    }
  }
}

public void rand() {
  actRandomSeed = (int) random(100000);
}
/* 
Part of the ReCode Project (http://recodeproject.com)
Based on "Random Squares" by Charles Csuri
Originally published in "Computer Graphics and Art" vol1 no2, 1976
Copyright (c) 2012 Chris Allick - OSI/MIT license (http://recodeproject/license).
Edited for The 70s Party Project by Giorgos Petkakis, 2017
*/

// These are the bigger 'foreground' rounded squares
public void rects() {
    for (int n = 0; n < fft.specSize(); n++)
  {
    if (fft.getBand(n)>fft.getBand(highest))
      highest=n;
      
          if (highest > highestEver)
      highestEver = highest;
  }
  // Set stroke parameters
  if(highest > 250) {
    fill(125 * highest/250, 0, highest * strokeWeightVolume);
  } else if (highest > 30) {
    fill(highest * strokeWeightVolume,  125 * highest/250, 0);
  } else {
    fill(0, 255 * strokeWeightVolume, 255 * highest/50, 255 * strokeWeightVolume);
  }
  
  
  // Draw ellipses
  for( int i = 1; i < 80 * strokeWeightVolume; i++ ) {
    strokeWeight(0.2f * strokeWeightVolume);
    ellipse( x(highest+y(highest / i)), y(highest+x(highest / i)), random(4, 8), random(4, 8));
  }
  System.out.println(highest);
}

public float x (float in)
{
  return sin(in)*height/2 + cos(in/20) * height/4 + sin(in/y(in))*height/4;
}

public float y (float in)
{
  return sin(in)*height/2 * cos(in/height);
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
      stroke(random(0, 1) * (255  * strokeWeightVolume), 0, random(0,1) * (255 * strokeWeightVolume));
      strokeWeight(random(0,1) * 0.5f * strokeWeightVolume);
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
  public void settings() {  size(800,600,P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#000000", "--hide-stop", "main" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}

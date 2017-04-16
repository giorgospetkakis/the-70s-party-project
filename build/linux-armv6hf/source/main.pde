import ddf.minim.*;
import ddf.minim.analysis.*;

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

void setup()
{
  //set up graphics
  //smooth();
  background(100);
  //size(800,800,P3D);
  fullScreen();
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

void draw()
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

void captureSound()
{
  fft.forward(in.left);
  beat.detect(in.left);
}

void generate()
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

void keyPressed()
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
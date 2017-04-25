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
int volumeControl = 100;
float rotation_main = 0;
int highest, highestEver;
boolean modeSwitch = true;

void setup()
{
  //set up graphics
  background(0);
  size(800,600,P3D);
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

void draw()
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
      fadeOut();
      break;
    case 1:
      fadeOut();
      rects();
      if(beat.isOnset())
        distort(3);
      break;
    case 2:
      draw3d();
      rects();
      distort(8);
      if(beat.isOnset())
        clear();
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
      volumeControl = 100;
      frameRate(100);
      break;
    }
    case '2':{
      volumeControl = 5;
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
    case '3':{
      if (size == 4)
        size = 1;
      else
        size = 4;
      break;
    }
    case '4':{
      volumeControl = 0;
      break;
    }
    case '5':{
      volumeControl = 25;
      break;
    }
    case '6':{
      volumeControl = 50;
      break;
    }
    case '7':{
      volumeControl = 75;
      break;
    }
    case ' ':{
      clear();
      break;
    }
  }
}

void distort(int mult){
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
  
    w = (int) random(16 * mult * strokeWeightVolume, 16 * mult * strokeWeightVolume);
    h = (int) random(16 * mult * strokeWeightVolume, 16 * mult * strokeWeightVolume);

    copy(x1,y1, w,h, x2,y2, w,h);
}
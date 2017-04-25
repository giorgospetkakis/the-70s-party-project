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
 
import processing.opengl.*;

float tileCount = 10;
color moduleColor = color(204);
int moduleAlpha = 100;
int actRandomSeed = 0;
int max_distance = 500; 

void draw3d() {
  float xMusicFollow = map(strokeWeightVolume, 0f, volumeControl * 1.5f, width/4f, 3*(float)width/4);
  float yMusicFollow = map(strokeWeightVolume, 0f, volumeControl * 1.5f, height/4f, 3*(float)height/4);
  noFill();
  actRandomSeed = (int) random(strokeWeightVolume*100);
  randomSeed(actRandomSeed);

  stroke(moduleColor, moduleAlpha);
  strokeWeight(0.2 * strokeWeightVolume);

  for (int gridY=-width/4; gridY<width * 1.15; gridY+=width/tileCount) {
    for (int gridX=-height/4; gridX<height * 1.15; gridX+=height/tileCount) {

      float diameter = dist(xMusicFollow, yMusicFollow, gridX, gridY);
      diameter = diameter/max_distance * 40;
      pushMatrix();
        ellipse(gridX + diameter * 3, gridY + diameter * 3, diameter, diameter);    //// also nice: ellipse(...)
      popMatrix(); 
    }
  }
}

void rand() {
  actRandomSeed = (int) random(100000);
}
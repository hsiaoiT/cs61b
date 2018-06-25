public class NBody{
  public static double readRadius(String fileName) {
    In in = new In(fileName);
    in.readInt();
    double radius = in.readDouble();
    return radius;
  }

  public static Planet[] readPlanets(String fileNamePath) {
    In file = new In(fileNamePath);
    int len = file.readInt();
    Planet[] planets = new Planet[len];
    file.readDouble();
    for (int i = 0; i < len; i ++) {
      double xPos = file.readDouble();
      double yPos = file.readDouble();
      double xVel = file.readDouble();
      double yVel = file.readDouble();
      double mass = file.readDouble();
      String imgFileName = file.readString();
      planets[i] = new Planet(xPos, yPos, xVel, yVel, mass, imgFileName);
    }
    return planets;
  }

//Draw the initial universe state
  public static void main(String[] args) {
    // Collect all needed input
    double T, dt;
    String filename;

    T = Double.parseDouble(args[0]);
    dt = Double.parseDouble(args[1]);
    filename = args[2];

    Planet[] planets = readPlanets(filename);
    double radius = readRadius(filename);
    int len = planets.length;

    // Draw the background
    String backg = "images/starfield.jpg";
    StdDraw.setScale(-radius, radius);
    StdDraw.clear();
    StdDraw.picture(0, 0, backg);
    // Draw planets
    for (int i = 0; i < len; i ++) {
      planets[i].draw();
    }

    //Create an animation
    StdDraw.enableDoubleBuffering();
    for (double t = 0; t <= T; t += dt) {
      double[] xForces =  new double[len];
      double[] yForces =  new double[len];
      for(int i = 0; i < len; i ++) {
        xForces[i] = planets[i].calcNetForceExertedByX(planets);
        yForces[i] = planets[i].calcNetForceExertedByY(planets);
      }
      for(int j = 0; j < len; j ++) {
        planets[j].update(dt, xForces[j], yForces[j]);
      }
      //draw with update
      StdDraw.picture(0, 0, backg);
      for (int i = 0; i < len; i ++) {
        planets[i].draw();
      }
      StdDraw.show();
      StdDraw.pause(10); //wait 10 milliseconds
    }

    StdAudio.play("audio/2001.mid"); //cannot play

    //Print the universe
    StdOut.printf("%d\n", len);
    StdOut.printf("%.2e\n", radius);
    for (int i = 0; i < len; i ++) {
      StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
    }
  }

}

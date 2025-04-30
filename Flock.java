ArrayList<Boid> flock = new ArrayList<Boid>();

void setup() {
  size(900, 600);
  // Create initial boids
  for (int i = 0; i < 100; i++) {
    flock.add(new Boid(random(width), random(height)));
  }
}

void draw() {
  background(220); // light gray background
  for (Boid b : flock) {
    b.flock(flock);
    b.update();
    b.wrap();
    b.display();
  }
}

class Boid {
  PVector pos, vel, acc;
  float maxSpeed = 2, maxForce = 0.05, view = 50;

  Boid(float x, float y) {
    pos = new PVector(x, y);
    vel = PVector.random2D().mult(random(1, maxSpeed));
    acc = new PVector();
  }

  void update() {
    vel.add(acc);
    vel.limit(maxSpeed);
    pos.add(vel);
    acc.mult(0);
  }

  void apply(PVector f) {
    acc.add(f);
  }

  void flock(ArrayList<Boid> boids) {
    PVector sep = new PVector();
    PVector ali = new PVector();
    PVector coh = new PVector();
    int total = 0;

    for (Boid o : boids) {
      float d = PVector.dist(pos, o.pos);
      if (o != this && d < view) {
        ali.add(o.vel);
        coh.add(o.pos);
        sep.add(PVector.sub(pos, o.pos).div(d * d));
        total++;
      }
    }

    if (total > 0) {
      ali.div(total).setMag(maxSpeed).sub(vel).limit(maxForce);
      coh.div(total).sub(pos).setMag(maxSpeed).sub(vel).limit(maxForce);
      sep.div(total).setMag(maxSpeed).sub(vel).limit(maxForce);
      apply(ali);
      apply(coh);
      apply(sep);
    }
  }

  void wrap() {
    if (pos.x < 0) pos.x = width;
    if (pos.x > width) pos.x = 0;
    if (pos.y < 0) pos.y = height;
    if (pos.y > height) pos.y = 0;
  }

  void display() {
    // Draw a black triangle pointing in velocity direction
    float a = vel.heading() + HALF_PI;
    pushMatrix();
    translate(pos.x, pos.y);
    rotate(a);
    fill(0);       // black fill
    noStroke();
    beginShape();
    vertex(0, -6);
    vertex(-4, 6);
    vertex(4, 6);
    endShape(CLOSE);
    popMatrix();
  }
}

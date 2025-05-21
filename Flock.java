ArrayList<Boid> flock = new ArrayList<Boid>();

void setup() {
  size(900, 600);
  // Create 100 boids at random spots
  for (int i = 0; i < 100; i++) {
    flock.add(new Boid(random(width), random(height)));
  }
}

void draw() {
  background(220); // light gray background
  for (Boid b : flock) {
    b.flock(flock);  // calculate steering forces based on neighbors
    b.update();      // update position and velocity
    b.wrap();        // wrap around edges
    b.display();     // draw boid
  }
}

class Boid {
  PVector pos, vel, acc;
  float maxSpeed = 2, maxForce = 0.05, view = 50;

  Boid(float x, float y) {
    pos = new PVector(x, y);
    vel = PVector.random2D().mult(random(1, maxSpeed)); // start with random velocity
    acc = new PVector();
  }

  void update() {
    vel.add(acc);        // add acceleration to velocity
    vel.limit(maxSpeed); // limit speed
    pos.add(vel);        // move position
    acc.mult(0);         // reset acceleration
  }

  void apply(PVector f) {
    acc.add(f);          // add force to acceleration
  }

  void flock(ArrayList<Boid> boids) {
    PVector sep = new PVector();  // separation force
    PVector ali = new PVector();  // alignment force
    PVector coh = new PVector();  // cohesion force
    int total = 0;

    for (Boid o : boids) {
      float d = PVector.dist(pos, o.pos);
      if (o != this && d < view) {
        ali.add(o.vel);                       // average velocity for alignment
        coh.add(o.pos);                       // average position for cohesion
        sep.add(PVector.sub(pos, o.pos).div(d * d)); // stronger repulsion when closer
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
    // wrap edges so boids appear on opposite side when crossing boundaries
    if (pos.x < 0) pos.x = width;
    if (pos.x > width) pos.x = 0;
    if (pos.y < 0) pos.y = height;
    if (pos.y > height) pos.y = 0;
  }

  void display() {
    // draw triangle pointing direction of velocity
    float a = vel.heading() + HALF_PI;
    pushMatrix();
    translate(pos.x, pos.y);
    rotate(a);
    fill(0);
    noStroke();
    beginShape();
    vertex(0, -6);
    vertex(-4, 6);
    vertex(4, 6);
    endShape(CLOSE);
    popMatrix();
  }
}

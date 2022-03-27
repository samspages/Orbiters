import java.awt.Color;
import java.util.*;

/**
 * An Orbiter is an object that orbits some other object, called its parent. 
 * The center of an orbital system is an Orbiter with no parent. 
 * Each Orbiter may have child Oribters that orbit it. 
 *
 * An Orbiter stores its orbital radius and current orbit angle.
 */

public class Orbiter {
  
  public enum Type {
    CIRCLE, SQUARE, TRIANGLE
  }

  private final double orbitRadius;
  private final Type type; 
  private final Color fillColor; 
  private double orbitAngle; 
  private double orbitSpeed;
  
  private final List<Orbiter> children = new LinkedList<Orbiter>();
  private final Orbiter parent; 
  
  public Orbiter(Orbiter parent, double orbitRadius, double orbitAngle, double orbitSpeed, Type type, Color fillColor) {
    this.orbitRadius = orbitRadius; 
    this.orbitAngle = orbitAngle; 
    this.type = type;
    this.fillColor = fillColor; 
    this.parent = parent; 
    this.orbitSpeed = orbitSpeed; 
    if (parent != null) parent.children.add(this);
  }
  
  public double getOrbitRadius() { return orbitRadius; }
  public double getOrbitAngle() { return orbitAngle; }
  public Color getFillColor() { return fillColor; }
  public Type getType() { return type; }
  public Orbiter getParent() { return parent; }
  public List<Orbiter> getChildren() { return children; }
  
  /**
   * Updates the rotation of this orbiter by the amount specified in the deltaAngle parameter. 
   * @param deltaAngle The amount of rotation angle to add the to the current rotation. 
   */
  public void updateRotation(double timeDelta) {
    orbitAngle += (timeDelta * orbitSpeed); 
  }
  
  public Matrix getMatrix() throws UndefinedMatrixOpException {
    if (parent == null) {
      return Matrix.identity(3);
    } else {
      Matrix rotate = Matrix.rotationH2D(this.orbitAngle);
      Matrix translate = Matrix.translationH2D(this.orbitRadius, this.orbitSpeed);
      Matrix ans = rotate.dot(translate);
      return parent.getMatrix().dot(ans);
    }
  }
}

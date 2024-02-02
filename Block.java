//207323411 Itamar Shapira
//207272220 Dolev Menajem

public class Block implements Collidable {
    private Rectangle shape;
    
    Rectangle getCollisionRectangle() {
        return this.shape;
    }

    Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        Line[] shapeBorders = this.shape.getBorders();
        double dx = currentVelocity.getdx();
        double dy = currentVelocity.getdy();
        for(int i = 0; i < 4; i++) {
            if(shapeBorders[i].isPointInLine(collisionPoint)) {
                if(i % 2 == 0) {
                    dy = -dy;
                } else {
                    dx = -dx;
                }
            }
        }
    }
}

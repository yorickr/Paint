package Game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class Draw
{

    protected double x, y;
    protected int width, height;
    protected BufferedImage bufferedImage;
    protected BufferedImage currentSprite;

    //affinetransform
    double rotation, scale;

    public Draw( int x, int y, BufferedImage bufferedImage, double rotation, double scale )
    {
        this.bufferedImage = bufferedImage;
        this.x = x;
        this.y = y;
        this.width = this.bufferedImage.getWidth();
        this.height = this.bufferedImage.getHeight();
        this.rotation = rotation;
        this.scale = scale;
        this.currentSprite = bufferedImage;
    }

    protected AffineTransform affineTransform()
    {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate( this.x, this.y );
        affineTransform.scale( this.scale, this.scale );
        affineTransform.rotate( Math.toRadians( this.rotation ), this.width / 2, this.height / 2 );
        return affineTransform;
    }

    public void drawCollision( Graphics2D g2 )
    {
        g2.draw( this.affineTransform().createTransformedShape( new Rectangle( 0, 0, this.width, this.height ) ).getBounds2D() );
    }

    public boolean checkCollision( Draw d )
    {
        Rectangle2D r1 = this.affineTransform().createTransformedShape( new Rectangle( 0, 0, this.width, this.height ) ).getBounds2D();
        Rectangle2D r2 = d.affineTransform().createTransformedShape( new Rectangle( 0, 0, d.width, d.height ) ).getBounds2D();

        if( r1.intersects( r2 ) )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * the draw method, draws the image with the affinetransform to the g2 object.
     *
     * @param g2
     */
    public void draw( Graphics2D g2 )
    {
        g2.drawImage( this.currentSprite, this.affineTransform(), null );
    }

    /**
     * add the x and y to the x and y position
     *
     * @param x to add to the x
     * @param y to add to the y
     */
    public void updatePosition( double x, double y )
    {
        this.x += x;
        this.y += y;
    }

    /**
     * Set the position of the object to a position
     *
     * @param x the x value of the object
     * @param y the y value of the object
     */
    public void setPosition( double x, double y )
    {
        this.x = x;
        this.y = y;
    }

    /**
     * add the rotation to the rotation (new rotation = old rotation + rotation)
     *
     * @param rotation
     */
    public void updateRotation( double rotation )
    {
        this.rotation += rotation;
        if( this.rotation > 360 )
        {
            this.rotation -= 360;
        }
    }

    public Point2D getPosition()
    {
        return new Point2D.Double( x, y );
    }

    /**
     * Set the position of the opbect to a position
     *
     * @param point of the object
     */
    public void setPosition( Point2D point )
    {
        setPosition( point.getX(), point.getY() );
    }

    public int getHeight()
    {
        return height;
    }

    public int getWidth()
    {
        return width;
    }

    public double getX()
    {
        return x;
    }

    public void setX( double x )
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY( double y )
    {
        this.y = y;
    }

    public void setCurrentSprite( BufferedImage currentSprite )
    {
        this.currentSprite = currentSprite;
        this.width = currentSprite.getWidth();
        this.height = currentSprite.getHeight();
    }

    public void setRotation( double rotation )
    {
        this.rotation = rotation;
    }
}

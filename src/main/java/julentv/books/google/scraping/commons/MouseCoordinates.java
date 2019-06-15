package julentv.books.google.scraping.commons;

import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Coordinates;

public class MouseCoordinates implements Coordinates {

    private final Point point;

    public MouseCoordinates(Point point) {
        this.point = point;
    }

    @Override
    public Point onScreen() {
        return point;
    }

    @Override
    public Point inViewPort() {
        return point;
    }

    @Override
    public Point onPage() {
        return point;
    }

    @Override
    public Object getAuxiliary() {
        return null;
    }
}

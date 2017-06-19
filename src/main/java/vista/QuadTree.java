package vista;

public class QuadTree
{
    public QuadTree(float x, float y, float sizeX, float sizeY)
    {
        root = new QuadTreeNode(x, y, sizeX, sizeY);
    }

    public void add(float x, float y, float sizeX, float sizeY)
    {
        root.add(x, y, sizeX, sizeY);
    }

    public boolean query(float x, float y, float sizeX, float sizeY)
    {
        return root.query(x, y, sizeX, sizeY);
    }

    private QuadTreeNode root;

    private class QuadTreeNode
    {
        public QuadTreeNode(float x, float y, float sizeX, float sizeY)
        {
            xQuad = x;
            yQuad = y;
            sizeQuadX = sizeX;
            sizeQuadY = sizeY;
            nw = ne = sw = se = null;
            amountChildren = 0;
        }

        public void add(float x, float y, float sizeX, float sizeY)
        {
            float halfSizeQuadX, halfSizeQuadY;
            if (amountChildren > 0)
            {
                amountChildren++;
                insertOnChild(x, y, sizeX, sizeY);
                if (sizeElementX > 0)
                    insertOnChild(xElement, yElement, sizeElementX, sizeElementY);
                xElement = yElement = sizeElementX = sizeElementY = 0;
            }
            else
            {
                insertHere(x, y, sizeX, sizeY);
                halfSizeQuadX = sizeQuadX / 2;
                halfSizeQuadY = sizeQuadY / 2;
                nw = new QuadTreeNode(xQuad, yQuad, halfSizeQuadX, halfSizeQuadY);
                ne = new QuadTreeNode(xQuad + halfSizeQuadX, yQuad, halfSizeQuadX, halfSizeQuadY);
                sw = new QuadTreeNode(xQuad, yQuad + halfSizeQuadY, halfSizeQuadX, halfSizeQuadY);
                se = new QuadTreeNode(xQuad + halfSizeQuadX, yQuad + halfSizeQuadY, halfSizeQuadX, halfSizeQuadY);
            }
        }

        public boolean query(float x, float y, float sizeX, float sizeY)
        {
            if (amountChildren > 1)
                return queryOnChild(x, y, sizeX, sizeY);
            else
                return intersect(xElement, yElement, sizeElementX, sizeElementY, x, y, sizeX, sizeY);
        }

        private void insertOnChild(float x, float y, float sizeX, float sizeY)
        {
            float halfSizeQuadX = sizeQuadX / 2;
            float halfSizeQuadY = sizeQuadY / 2;
            if (intersect(x, y, sizeX, sizeY, xQuad, yQuad, halfSizeQuadX, halfSizeQuadY))
                nw.add(x, y, sizeX, sizeY);
            if (intersect(x, y, sizeX, sizeY, xQuad + halfSizeQuadX, yQuad, halfSizeQuadX, halfSizeQuadY))
                ne.add(x, y, sizeX, sizeY);
            if (intersect(x, y, sizeX, sizeY, xQuad, yQuad + halfSizeQuadY, halfSizeQuadX, halfSizeQuadY))
                sw.add(x, y, sizeX, sizeY);
            if (intersect(x, y, sizeX, sizeY, xQuad + halfSizeQuadX, yQuad + halfSizeQuadY, halfSizeQuadX, halfSizeQuadY))
                se.add(x, y, sizeX, sizeY);
        }

        private boolean queryOnChild(float x, float y, float sizeX, float sizeY)
        {
            float halfSizeQuadX = sizeQuadX / 2;
            float halfSizeQuadY = sizeQuadY / 2;
            boolean queryResult = false;
            if (intersect(x, y, sizeX, sizeY, xQuad, yQuad, halfSizeQuadX, halfSizeQuadY))
                queryResult |= nw.query(x, y, sizeX, sizeY);
            if (intersect(x, y, sizeX, sizeY, xQuad + halfSizeQuadX, yQuad, halfSizeQuadX, halfSizeQuadY))
                queryResult |= ne.query(x, y, sizeX, sizeY);
            if (intersect(x, y, sizeX, sizeY, xQuad, yQuad + halfSizeQuadY, halfSizeQuadX, halfSizeQuadY))
                queryResult |= sw.query(x, y, sizeX, sizeY);
            if (intersect(x, y, sizeX, sizeY, xQuad + halfSizeQuadX, yQuad + halfSizeQuadY, halfSizeQuadX, halfSizeQuadY))
                queryResult |= se.query(x, y, sizeX, sizeY);
            return queryResult;
        }

        private boolean intersect(float x1, float y1, float sizeX1, float sizeY1, float x2, float y2, float sizeX2, float sizeY2)
        {
            return vertexOnSquare(x1, y1, sizeX1, sizeY1, x2, y2) ||
                    vertexOnSquare(x1, y1, sizeX1, sizeY1, x2 + sizeX2, y2) ||
                    vertexOnSquare(x1, y1, sizeX1, sizeY1, x2, y2 + sizeY2) ||
                    vertexOnSquare(x1, y1, sizeX1, sizeY1, x2 + sizeX2, y2 + sizeY2) ||
                    vertexOnSquare(x2, y2, sizeX2, sizeY2, x1, y1) ||
                    vertexOnSquare(x2, y2, sizeX2, sizeY2, x1 + sizeX1, y1) ||
                    vertexOnSquare(x2, y2, sizeX2, sizeY2, x1, y1 + sizeY1) ||
                    vertexOnSquare(x2, y2, sizeX2, sizeY2, x1 + sizeX1, y1 + sizeY1);
        }

        private boolean vertexOnSquare(float x1, float y1, float sizeX1, float sizeY1, float x2, float y2)
        {
            return x1 <= x2 && x2 <= x1 + sizeX1 && y1 <= y2 && y2 <= y1 + sizeY1;
        }

        private void insertHere(float x, float y, float sizeX, float sizeY)
        {
            xElement = x;
            yElement = y;
            sizeElementX = sizeX;
            sizeElementY = sizeY;
            amountChildren++;
        }

        private float xQuad, yQuad;
        private float sizeQuadX, sizeQuadY;
        private float xElement, yElement;
        private float sizeElementX, sizeElementY;
        private QuadTreeNode nw, ne, sw, se;
        private int amountChildren;

    }

}

package com.elysian.client.util.summit;

import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.BufferUtils;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public final class GLUProjection
{
    private static GLUProjection instance;
    private IntBuffer viewport;
    private FloatBuffer modelview;
    private FloatBuffer projection;
    private FloatBuffer coords;
    private Vector3D frustumPos;
    private Vector3D[] frustum;
    private Vector3D[] invFrustum;
    private Vector3D viewVec;
    private double displayWidth;
    private double displayHeight;
    private double widthScale;
    private double heightScale;
    private double bra;
    private double bla;
    private double tra;
    private double tla;
    private Line tb;
    private Line bb;
    private Line lb;
    private Line rb;
    private float fovY;
    private float fovX;
    private Vector3D lookVec;
    
    private GLUProjection() {
        coords = BufferUtils.createFloatBuffer(3);
    }
    
    public static GLUProjection getInstance() {
        if (GLUProjection.instance == null) {
            GLUProjection.instance = new GLUProjection();
        }
        return GLUProjection.instance;
    }
    
    public void updateMatrices(final IntBuffer viewport, final FloatBuffer modelview, final FloatBuffer projection, final double widthScale, final double heightScale) {
        this.viewport = viewport;
        this.modelview = modelview;
        this.projection = projection;
        this.widthScale = widthScale;
        this.heightScale = heightScale;
        final float fov = (float)Math.toDegrees(Math.atan(1.0 / projection.get(5)) * 2.0);
        fovY = fov;
        displayWidth = viewport.get(2);
        displayHeight = viewport.get(3);
        fovX = (float)Math.toDegrees(2.0 * Math.atan(displayWidth / displayHeight * Math.tan(Math.toRadians(fovY) / 2.0)));
        final Vector3D lv = new Vector3D(modelview.get(0), modelview.get(1), modelview.get(2));
        final Vector3D uv = new Vector3D(modelview.get(4), modelview.get(5), modelview.get(6));
        final Vector3D fv = new Vector3D(modelview.get(8), modelview.get(9), modelview.get(10));
        final Vector3D nuv = new Vector3D(0.0, 1.0, 0.0);
        final Vector3D nlv = new Vector3D(1.0, 0.0, 0.0);
        double yaw = Math.toDegrees(Math.atan2(nlv.cross(lv).length(), nlv.dot(lv))) + 180.0;
        if (fv.x < 0.0) {
            yaw = 360.0 - yaw;
        }
        double pitch = 0.0;
        if ((-fv.y > 0.0 && yaw >= 90.0 && yaw < 270.0) || (fv.y > 0.0 && (yaw < 90.0 || yaw >= 270.0))) {
            pitch = Math.toDegrees(Math.atan2(nuv.cross(uv).length(), nuv.dot(uv)));
        }
        else {
            pitch = -Math.toDegrees(Math.atan2(nuv.cross(uv).length(), nuv.dot(uv)));
        }
        lookVec = getRotationVector(yaw, pitch);
        final Matrix4f modelviewMatrix = new Matrix4f();
        modelviewMatrix.load(modelview.asReadOnlyBuffer());
        modelviewMatrix.invert();
        frustumPos = new Vector3D(modelviewMatrix.m30, modelviewMatrix.m31, modelviewMatrix.m32);
        frustum = getFrustum(frustumPos.x, frustumPos.y, frustumPos.z, yaw, pitch, fov, 1.0, displayWidth / displayHeight);
        invFrustum = getFrustum(frustumPos.x, frustumPos.y, frustumPos.z, yaw - 180.0, -pitch, fov, 1.0, displayWidth / displayHeight);
        viewVec = getRotationVector(yaw, pitch).normalized();
        bra = Math.toDegrees(Math.acos(displayHeight * heightScale / Math.sqrt(displayWidth * widthScale * displayWidth * widthScale + displayHeight * heightScale * displayHeight * heightScale)));
        bla = 360.0 - bra;
        tra = bla - 180.0;
        tla = bra + 180.0;
        rb = new Line(displayWidth * widthScale, 0.0, 0.0, 0.0, 1.0, 0.0);
        tb = new Line(0.0, 0.0, 0.0, 1.0, 0.0, 0.0);
        lb = new Line(0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
        bb = new Line(0.0, displayHeight * heightScale, 0.0, 1.0, 0.0, 0.0);
    }
    
    public Projection project(final double x, final double y, final double z, final ClampMode clampModeOutside, final boolean extrudeInverted) {
        if (viewport != null && modelview != null && projection != null) {
            final Vector3D posVec = new Vector3D(x, y, z);
            final boolean[] frustum = doFrustumCheck(this.frustum, frustumPos, x, y, z);
            final boolean outsideFrustum = frustum[0] || frustum[1] || frustum[2] || frustum[3];
            if (outsideFrustum) {
                final boolean opposite = posVec.sub(frustumPos).dot(viewVec) <= 0.0;
                final boolean[] invFrustum = doFrustumCheck(this.invFrustum, frustumPos, x, y, z);
                final boolean outsideInvertedFrustum = invFrustum[0] || invFrustum[1] || invFrustum[2] || invFrustum[3];
                if ((extrudeInverted && !outsideInvertedFrustum) || (outsideInvertedFrustum && clampModeOutside != ClampMode.NONE)) {
                    if ((extrudeInverted && !outsideInvertedFrustum) || (clampModeOutside == ClampMode.DIRECT && outsideInvertedFrustum)) {
                        double vecX = 0.0;
                        double vecY = 0.0;
                        if (GLU.gluProject((float)x, (float)y, (float)z, modelview, projection, viewport, coords)) {
                            if (opposite) {
                                vecX = displayWidth * widthScale - coords.get(0) * widthScale - displayWidth * widthScale / 2.0;
                                vecY = displayHeight * heightScale - (displayHeight - coords.get(1)) * heightScale - displayHeight * heightScale / 2.0;
                            }
                            else {
                                vecX = coords.get(0) * widthScale - displayWidth * widthScale / 2.0;
                                vecY = (displayHeight - coords.get(1)) * heightScale - displayHeight * heightScale / 2.0;
                            }
                            final Vector3D vec = new Vector3D(vecX, vecY, 0.0).snormalize();
                            vecX = vec.x;
                            vecY = vec.y;
                            final Line vectorLine = new Line(displayWidth * widthScale / 2.0, displayHeight * heightScale / 2.0, 0.0, vecX, vecY, 0.0);
                            double angle = Math.toDegrees(Math.acos(vec.y / Math.sqrt(vec.x * vec.x + vec.y * vec.y)));
                            if (vecX < 0.0) {
                                angle = 360.0 - angle;
                            }
                            Vector3D intersect = new Vector3D(0.0, 0.0, 0.0);
                            if (angle >= bra && angle < tra) {
                                intersect = rb.intersect(vectorLine);
                            }
                            else if (angle >= tra && angle < tla) {
                                intersect = tb.intersect(vectorLine);
                            }
                            else if (angle >= tla && angle < bla) {
                                intersect = lb.intersect(vectorLine);
                            }
                            else {
                                intersect = bb.intersect(vectorLine);
                            }
                            return new Projection(intersect.x, intersect.y, outsideInvertedFrustum ? Projection.Type.OUTSIDE : Projection.Type.INVERTED);
                        }
                        return new Projection(0.0, 0.0, Projection.Type.FAIL);
                    }
                    else if (clampModeOutside == ClampMode.ORTHOGONAL && outsideInvertedFrustum) {
                        if (GLU.gluProject((float)x, (float)y, (float)z, modelview, projection, viewport, coords)) {
                            double guiX = coords.get(0) * widthScale;
                            double guiY = (displayHeight - coords.get(1)) * heightScale;
                            if (opposite) {
                                guiX = displayWidth * widthScale - guiX;
                                guiY = displayHeight * heightScale - guiY;
                            }
                            if (guiX < 0.0) {
                                guiX = 0.0;
                            }
                            else if (guiX > displayWidth * widthScale) {
                                guiX = displayWidth * widthScale;
                            }
                            if (guiY < 0.0) {
                                guiY = 0.0;
                            }
                            else if (guiY > displayHeight * heightScale) {
                                guiY = displayHeight * heightScale;
                            }
                            return new Projection(guiX, guiY, outsideInvertedFrustum ? Projection.Type.OUTSIDE : Projection.Type.INVERTED);
                        }
                        return new Projection(0.0, 0.0, Projection.Type.FAIL);
                    }
                }
                else {
                    if (GLU.gluProject((float)x, (float)y, (float)z, modelview, projection, viewport, coords)) {
                        double guiX = coords.get(0) * widthScale;
                        double guiY = (displayHeight - coords.get(1)) * heightScale;
                        if (opposite) {
                            guiX = displayWidth * widthScale - guiX;
                            guiY = displayHeight * heightScale - guiY;
                        }
                        return new Projection(guiX, guiY, outsideInvertedFrustum ? Projection.Type.OUTSIDE : Projection.Type.INVERTED);
                    }
                    return new Projection(0.0, 0.0, Projection.Type.FAIL);
                }
            }
            else {
                if (GLU.gluProject((float)x, (float)y, (float)z, modelview, projection, viewport, coords)) {
                    final double guiX2 = coords.get(0) * widthScale;
                    final double guiY2 = (displayHeight - coords.get(1)) * heightScale;
                    return new Projection(guiX2, guiY2, Projection.Type.INSIDE);
                }
                return new Projection(0.0, 0.0, Projection.Type.FAIL);
            }
        }
        return new Projection(0.0, 0.0, Projection.Type.FAIL);
    }
    
    public boolean[] doFrustumCheck(final Vector3D[] frustumCorners, final Vector3D frustumPos, final double x, final double y, final double z) {
        final Vector3D point = new Vector3D(x, y, z);
        final boolean c1 = crossPlane(new Vector3D[] { frustumPos, frustumCorners[3], frustumCorners[0] }, point);
        final boolean c2 = crossPlane(new Vector3D[] { frustumPos, frustumCorners[0], frustumCorners[1] }, point);
        final boolean c3 = crossPlane(new Vector3D[] { frustumPos, frustumCorners[1], frustumCorners[2] }, point);
        final boolean c4 = crossPlane(new Vector3D[] { frustumPos, frustumCorners[2], frustumCorners[3] }, point);
        return new boolean[] { c1, c2, c3, c4 };
    }
    
    public boolean crossPlane(final Vector3D[] plane, final Vector3D point) {
        final Vector3D z = new Vector3D(0.0, 0.0, 0.0);
        final Vector3D e0 = plane[1].sub(plane[0]);
        final Vector3D e2 = plane[2].sub(plane[0]);
        final Vector3D normal = e0.cross(e2).snormalize();
        final double D = z.sub(normal).dot(plane[2]);
        final double dist = normal.dot(point) + D;
        return dist >= 0.0;
    }
    
    public Vector3D[] getFrustum(final double x, final double y, final double z, final double rotationYaw, final double rotationPitch, final double fov, final double farDistance, final double aspectRatio) {
        final double hFar = 2.0 * Math.tan(Math.toRadians(fov / 2.0)) * farDistance;
        final double wFar = hFar * aspectRatio;
        final Vector3D view = getRotationVector(rotationYaw, rotationPitch).snormalize();
        final Vector3D up = getRotationVector(rotationYaw, rotationPitch - 90.0).snormalize();
        final Vector3D right = getRotationVector(rotationYaw + 90.0, 0.0).snormalize();
        final Vector3D camPos = new Vector3D(x, y, z);
        final Vector3D view_camPos_product = view.add(camPos);
        final Vector3D fc = new Vector3D(view_camPos_product.x * farDistance, view_camPos_product.y * farDistance, view_camPos_product.z * farDistance);
        final Vector3D topLeftfrustum = new Vector3D(fc.x + up.x * hFar / 2.0 - right.x * wFar / 2.0, fc.y + up.y * hFar / 2.0 - right.y * wFar / 2.0, fc.z + up.z * hFar / 2.0 - right.z * wFar / 2.0);
        final Vector3D downLeftfrustum = new Vector3D(fc.x - up.x * hFar / 2.0 - right.x * wFar / 2.0, fc.y - up.y * hFar / 2.0 - right.y * wFar / 2.0, fc.z - up.z * hFar / 2.0 - right.z * wFar / 2.0);
        final Vector3D topRightfrustum = new Vector3D(fc.x + up.x * hFar / 2.0 + right.x * wFar / 2.0, fc.y + up.y * hFar / 2.0 + right.y * wFar / 2.0, fc.z + up.z * hFar / 2.0 + right.z * wFar / 2.0);
        final Vector3D downRightfrustum = new Vector3D(fc.x - up.x * hFar / 2.0 + right.x * wFar / 2.0, fc.y - up.y * hFar / 2.0 + right.y * wFar / 2.0, fc.z - up.z * hFar / 2.0 + right.z * wFar / 2.0);
        return new Vector3D[] { topLeftfrustum, downLeftfrustum, downRightfrustum, topRightfrustum };
    }
    
    public Vector3D[] getFrustum() {
        return frustum;
    }
    
    public float getFovX() {
        return fovX;
    }
    
    public float getFovY() {
        return fovY;
    }
    
    public Vector3D getLookVector() {
        return lookVec;
    }
    
    public Vector3D getRotationVector(final double rotYaw, final double rotPitch) {
        final double c = Math.cos(-rotYaw * 0.01745329238474369 - 3.141592653589793);
        final double s = Math.sin(-rotYaw * 0.01745329238474369 - 3.141592653589793);
        final double nc = -Math.cos(-rotPitch * 0.01745329238474369);
        final double ns = Math.sin(-rotPitch * 0.01745329238474369);
        return new Vector3D(s * nc, ns, c * nc);
    }
    
    public static class Line
    {
        public Vector3D sourcePoint;
        public Vector3D direction;
        
        public Line(final double sx, final double sy, final double sz, final double dx, final double dy, final double dz) {
            sourcePoint = new Vector3D(0.0, 0.0, 0.0);
            direction = new Vector3D(0.0, 0.0, 0.0);
            sourcePoint.x = sx;
            sourcePoint.y = sy;
            sourcePoint.z = sz;
            direction.x = dx;
            direction.y = dy;
            direction.z = dz;
        }
        
        public Vector3D intersect(final Line line) {
            final double a = sourcePoint.x;
            final double b = direction.x;
            final double c = line.sourcePoint.x;
            final double d = line.direction.x;
            final double e = sourcePoint.y;
            final double f = direction.y;
            final double g = line.sourcePoint.y;
            final double h = line.direction.y;
            final double te = -(a * h - c * h - d * (e - g));
            final double be = b * h - d * f;
            if (be == 0.0) {
                return intersectXZ(line);
            }
            final double t = te / be;
            final Vector3D result = new Vector3D(0.0, 0.0, 0.0);
            result.x = sourcePoint.x + direction.x * t;
            result.y = sourcePoint.y + direction.y * t;
            result.z = sourcePoint.z + direction.z * t;
            return result;
        }
        
        private Vector3D intersectXZ(final Line line) {
            final double a = sourcePoint.x;
            final double b = direction.x;
            final double c = line.sourcePoint.x;
            final double d = line.direction.x;
            final double e = sourcePoint.z;
            final double f = direction.z;
            final double g = line.sourcePoint.z;
            final double h = line.direction.z;
            final double te = -(a * h - c * h - d * (e - g));
            final double be = b * h - d * f;
            if (be == 0.0) {
                return intersectYZ(line);
            }
            final double t = te / be;
            final Vector3D result = new Vector3D(0.0, 0.0, 0.0);
            result.x = sourcePoint.x + direction.x * t;
            result.y = sourcePoint.y + direction.y * t;
            result.z = sourcePoint.z + direction.z * t;
            return result;
        }
        
        private Vector3D intersectYZ(final Line line) {
            final double a = sourcePoint.y;
            final double b = direction.y;
            final double c = line.sourcePoint.y;
            final double d = line.direction.y;
            final double e = sourcePoint.z;
            final double f = direction.z;
            final double g = line.sourcePoint.z;
            final double h = line.direction.z;
            final double te = -(a * h - c * h - d * (e - g));
            final double be = b * h - d * f;
            if (be == 0.0) {
                return null;
            }
            final double t = te / be;
            final Vector3D result = new Vector3D(0.0, 0.0, 0.0);
            result.x = sourcePoint.x + direction.x * t;
            result.y = sourcePoint.y + direction.y * t;
            result.z = sourcePoint.z + direction.z * t;
            return result;
        }
        
        public Vector3D intersectPlane(final Vector3D pointOnPlane, final Vector3D planeNormal) {
            final Vector3D result = new Vector3D(sourcePoint.x, sourcePoint.y, sourcePoint.z);
            final double d = pointOnPlane.sub(sourcePoint).dot(planeNormal) / direction.dot(planeNormal);
            result.sadd(direction.mul(d));
            if (direction.dot(planeNormal) == 0.0) {
                return null;
            }
            return result;
        }
    }
    
    public static class Vector3D
    {
        public double x;
        public double y;
        public double z;
        
        public Vector3D(final double x, final double y, final double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        
        public Vector3D add(final Vector3D v) {
            return new Vector3D(x + v.x, y + v.y, z + v.z);
        }
        
        public Vector3D add(final double x, final double y, final double z) {
            return new Vector3D(x + x, y + y, z + z);
        }
        
        public Vector3D sub(final Vector3D v) {
            return new Vector3D(x - v.x, y - v.y, z - v.z);
        }
        
        public Vector3D sub(final double x, final double y, final double z) {
            return new Vector3D(x - x, y - y, z - z);
        }
        
        public Vector3D normalized() {
            final double len = Math.sqrt(x * x + y * y + z * z);
            return new Vector3D(x / len, y / len, z / len);
        }
        
        public double dot(final Vector3D v) {
            return x * v.x + y * v.y + z * v.z;
        }
        
        public Vector3D cross(final Vector3D v) {
            return new Vector3D(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
        }
        
        public Vector3D mul(final double m) {
            return new Vector3D(x * m, y * m, z * m);
        }
        
        public Vector3D div(final double d) {
            return new Vector3D(x / d, y / d, z / d);
        }
        
        public double length() {
            return Math.sqrt(x * x + y * y + z * z);
        }
        
        public Vector3D sadd(final Vector3D v) {
            x += v.x;
            y += v.y;
            z += v.z;
            return this;
        }
        
        public Vector3D sadd(final double x, final double y, final double z) {
            this.x += x;
            this.y += y;
            this.z += z;
            return this;
        }
        
        public Vector3D ssub(final Vector3D v) {
            x -= v.x;
            y -= v.y;
            z -= v.z;
            return this;
        }
        
        public Vector3D ssub(final double x, final double y, final double z) {
            this.x -= x;
            this.y -= y;
            this.z -= z;
            return this;
        }
        
        public Vector3D snormalize() {
            final double len = Math.sqrt(x * x + y * y + z * z);
            x /= len;
            y /= len;
            z /= len;
            return this;
        }
        
        public Vector3D scross(final Vector3D v) {
            x = y * v.z - z * v.y;
            y = z * v.x - x * v.z;
            z = x * v.y - y * v.x;
            return this;
        }
        
        public Vector3D smul(final double m) {
            x *= m;
            y *= m;
            z *= m;
            return this;
        }
        
        public Vector3D sdiv(final double d) {
            x /= d;
            y /= d;
            z /= d;
            return this;
        }
        
        @Override
        public String toString() {
            return "(X: " + x + " Y: " + y + " Z: " + z + ")";
        }
    }
    
    public static class Projection
    {
        private final double x;
        private final double y;
        private final Type t;
        
        public Projection(final double x, final double y, final Type t) {
            this.x = x;
            this.y = y;
            this.t = t;
        }
        
        public double getX() {
            return x;
        }
        
        public double getY() {
            return y;
        }
        
        public Type getType() {
            return t;
        }
        
        public boolean isType(final Type type) {
            return t == type;
        }
        
        public enum Type
        {
            INSIDE, 
            OUTSIDE, 
            INVERTED, 
            FAIL;
        }
    }
    
    public enum ClampMode
    {
        ORTHOGONAL, 
        DIRECT, 
        NONE;
    }
}

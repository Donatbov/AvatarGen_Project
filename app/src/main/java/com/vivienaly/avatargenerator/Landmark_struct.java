package com.vivienaly.avatargenerator;

public class Landmark_struct {
    private int noseBase_x;
    private int noseBase_y;
    private boolean noseBase_isSet;
    private int bottomMouth_x;
    private int bottomMouth_y;
    private boolean bottomMouth_isSet;
    private int leftMouth_x;
    private int leftMouth_y;
    private boolean leftMouth_isSet;
    private int rightMouth_x;
    private int rightMouth_y;
    private boolean rightMouth_isSet;
    private int leftCheek_x;
    private int leftCheek_y;
    private boolean leftCheek_isSet;
    private int rightCheek_x;
    private int rightCheek_y;
    private boolean rightCheek_isSet;
    private int leftEar_x;
    private int leftEar_y;
    private boolean leftEar_isSet;
    private int rigthEar_x;
    private int rigthEar_y;
    private boolean rigthEar_isSet;
    private int leftEarTip_x;
    private int leftEarTip_y;
    private boolean leftEarTip_isSet;
    private int rigthEarTip_x;
    private int rigthEarTip_y;
    private boolean rigthEarTip_isSet;
    private int leftEye_x;
    private int leftEye_y;
    private boolean leftEye_isSet;
    private int rigthEye_x;
    private int rigthEye_y;
    private boolean rigthEye_isSet;

    public Landmark_struct() {
        noseBase_isSet = false;
        bottomMouth_isSet = false;
        leftMouth_isSet = false;
        rightMouth_isSet = false;
        leftCheek_isSet = false;
        rightCheek_isSet = false;
        leftEar_isSet = false;
        rigthEar_isSet = false;
        leftEarTip_isSet = false;
        rigthEarTip_isSet = false;
        leftEye_isSet = false;
        rigthEye_isSet = false;
    }

    //accesseurs

    public int getNoseBase_x() {
        return noseBase_x;
    }

    public void setNoseBase_x(int noseBase_x) {
        this.noseBase_x = noseBase_x;
        this.noseBase_isSet = true;
    }

    public int getNoseBase_y() {
        return noseBase_y;
    }

    public void setNoseBase_y(int noseBase_y) {
        this.noseBase_y = noseBase_y;
        this.noseBase_isSet = true;
    }

    public int getBottomMouth_x() {
        return bottomMouth_x;
    }

    public void setBottomMouth_x(int bottomMouth_x) {
        this.bottomMouth_x = bottomMouth_x;
        this.bottomMouth_isSet = true;
    }

    public int getBottomMouth_y() {
        return bottomMouth_y;
    }

    public void setBottomMouth_y(int bottomMouth_y) {
        this.bottomMouth_y = bottomMouth_y;
        this.bottomMouth_isSet = true;
    }


    public int getLeftMouth_x() {
        return leftMouth_x;
    }

    public void setLeftMouth_x(int leftMouth_x) {
        this.leftMouth_x = leftMouth_x;
        this.leftMouth_isSet = true;
    }

    public int getLeftMouth_y() {
        return leftMouth_y;
    }

    public void setLeftMouth_y(int leftMouth_y) {
        this.leftMouth_y = leftMouth_y;
        this.leftMouth_isSet = true;
    }

    public int getRightMouth_x() {
        return rightMouth_x;
    }

    public void setRightMouth_x(int rightMouth_x) {
        this.rightMouth_x = rightMouth_x;
        this.rightMouth_isSet = true;
    }

    public int getRightMouth_y() {
        return rightMouth_y;
    }

    public void setRightMouth_y(int rightMouth_y) {
        this.rightMouth_y = rightMouth_y;
        this.rightMouth_isSet = true;
    }


    public int getLeftCheek_x() {
        return leftCheek_x;
    }

    public void setLeftCheek_x(int leftCheek_x) {
        this.leftCheek_x = leftCheek_x;
        this.leftCheek_isSet = true;
    }

    public int getLeftCheek_y() {
        return leftCheek_y;
    }

    public void setLeftCheek_y(int leftCheek_y) {
        this.leftCheek_y = leftCheek_y;
        this.leftCheek_isSet = true;
    }

    public int getRightCheek_x() {
        return rightCheek_x;
    }

    public void setRightCheek_x(int rightCheek_x) {
        this.rightCheek_x = rightCheek_x;
        this.rightCheek_isSet = true;
    }

    public int getRightCheek_y() {
        return rightCheek_y;
    }

    public void setRightCheek_y(int rightCheek_y) {
        this.rightCheek_y = rightCheek_y;
        this.rightCheek_isSet = true;
    }

    public int getLeftEar_x() {
        return leftEar_x;
    }

    public void setLeftEar_x(int leftEar_x) {
        this.leftEar_x = leftEar_x;
        this.leftEar_isSet = true;
    }

    public int getLeftEar_y() {
        return leftEar_y;
    }

    public void setLeftEar_y(int leftEar_y) {
        this.leftEar_y = leftEar_y;
        this.leftEar_isSet = true;
    }

    public int getRigthEar_x() {
        return rigthEar_x;
    }

    public void setRigthEar_x(int rigthEar_x) {
        this.rigthEar_x = rigthEar_x;
        this.rigthEar_isSet = true;
    }

    public int getRigthEar_y() {
        return rigthEar_y;
    }

    public void setRigthEar_y(int rigthEar_y) {
        this.rigthEar_y = rigthEar_y;
        this.rigthEar_isSet = true;
    }

    public int getLeftEarTip_x() {
        return leftEarTip_x;
    }

    public void setLeftEarTip_x(int leftEarTip_x) {
        this.leftEarTip_x = leftEarTip_x;
        this.leftEar_isSet = true;
    }

    public int getLeftEarTip_y() {
        return leftEarTip_y;
    }

    public void setLeftEarTip_y(int leftEarTip_y) {
        this.leftEarTip_y = leftEarTip_y;
        this.leftEar_isSet = true;
    }

    public int getRigthEarTip_x() {
        return rigthEarTip_x;
    }

    public void setRigthEarTip_x(int rigthEarTip_x) {
        this.rigthEarTip_x = rigthEarTip_x;
        this.rigthEarTip_isSet = true;
    }

    public int getRigthEarTip_y() {
        return rigthEarTip_y;
    }

    public void setRigthEarTip_y(int rigthEarTip_y) {
        this.rigthEarTip_y = rigthEarTip_y;
        this.rigthEarTip_isSet = true;
    }

    public int getLeftEye_x() {
        return leftEye_x;
    }

    public void setLeftEye_x(int leftEye_x) {
        this.leftEye_x = leftEye_x;
        this.leftEye_isSet = true;
    }

    public int getLeftEye_y() {
        return leftEye_y;
    }

    public void setLeftEye_y(int leftEye_y) {
        this.leftEye_y = leftEye_y;
        this.leftEye_isSet = true;
    }

    public int getRigthEye_x() {
        return rigthEye_x;
    }

    public void setRigthEye_x(int rigthEye_x) {
        this.rigthEye_x = rigthEye_x;
        this.rigthEye_isSet = true;
    }

    public int getRigthEye_y() {
        return rigthEye_y;
    }

    public void setRigthEye_y(int rigthEye_y) {
        this.rigthEye_y = rigthEye_y;
        this.rigthEye_isSet = true;
    }

    public boolean noseBase_isSet() {
        return noseBase_isSet;
    }

    public boolean bottomMouth_isSet() {
        return bottomMouth_isSet;
    }

    public boolean leftMouth_isSet() {
        return leftMouth_isSet;
    }

    public boolean rightMouth_isSet() {
        return rightMouth_isSet;
    }

    public boolean leftCheek_isSet() {
        return leftCheek_isSet;
    }

    public boolean rightCheek_isSet() {
        return rightCheek_isSet;
    }

    public boolean leftEar_isSet() {
        return leftEar_isSet;
    }

    public boolean rigthEar_isSet() {
        return rigthEar_isSet;
    }

    public boolean leftEarTip_isSet() {
        return leftEarTip_isSet;
    }

    public boolean rigthEarTip_isSet() {
        return rigthEarTip_isSet;
    }

    public boolean leftEye_isSet() {
        return leftEye_isSet;
    }

    public boolean rigthEye_isSet() {
        return rigthEye_isSet;
    }
}

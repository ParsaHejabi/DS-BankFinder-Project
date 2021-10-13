public class KDTree {
    public KDTree leftChild;
    public KDTree rightChild;
    //    0 = amoodi, 1 = ofoghi
    public boolean orientation;
    public Bank bank;
    //    moghe delete automat bankaro search ham bokone
    public Bank searchedBank;
    public boolean flag = true;

    public KDTree(boolean orientation, Bank bank) {
        leftChild = null;
        rightChild = null;
        this.orientation = orientation;
        this.bank = bank;
        this.searchedBank = null;
    }

    public KDTree add(KDTree root, Bank bank, boolean orientation) {

        if (root == null) {
            return new KDTree(orientation, bank);
        }
        if (root.bank.x == bank.x && root.bank.y == bank.y) {
            return null;
        }

        if (!orientation && bank.x < root.bank.x)
            root.leftChild = add(root.leftChild, bank, !orientation);

        if (!orientation && bank.x >= root.bank.x)
            root.rightChild = add(root.rightChild, bank, !orientation);

        if (orientation && bank.y < root.bank.y)
            root.leftChild = add(root.leftChild, bank, !orientation);

        if (orientation && bank.y >= root.bank.y)
            root.rightChild = add(root.rightChild, bank, !orientation);

        return root;
    }

    public KDTree delete(KDTree root, double x, double y, boolean orientation) {

        if (root == null) {
            return null;
        }

        if (root.bank.x == x && root.bank.y == y) {
            if (flag)
                this.searchedBank = root.bank;
            if (root.rightChild != null) {
                KDTree min = findMinimumOfOrientation(root.rightChild, root.orientation);

                flag = false;
                root.bank = min.bank;

                root.rightChild = delete(root.rightChild, min.bank.x, min.bank.y, !orientation);
            } else if (root.leftChild != null) {
                KDTree min = findMinimumOfOrientation(root.leftChild, root.orientation);

                root.bank = min.bank;

                root.rightChild = delete(root.leftChild, min.bank.x, min.bank.y, !orientation);
            } else {
                root.bank = null;
                return null;
            }
            return root;
        }

        if (!orientation) {
            if (x < root.bank.x) {
                root.leftChild = delete(root.leftChild, x, y, !orientation);
            } else {
                root.rightChild = delete(root.rightChild, x, y, !orientation);
            }
        } else {
            if (y < root.bank.y) {
                root.leftChild = delete(root.leftChild, x, y, !orientation);
            } else {
                root.rightChild = delete(root.rightChild, x, y, !orientation);
            }
        }

        return root;
    }

    public KDTree findMinimumOfOrientation(KDTree root, boolean subTreeOrientation) {

        if (root == null) {
            return null;
        }

        if (root.orientation == subTreeOrientation) {
            if (root.leftChild == null)
                return root;
            return findMinimumOfOrientation(root.leftChild, subTreeOrientation);
        }

        if (root.leftChild != null && root.rightChild != null) {
            KDTree leftChild = findMinimumOfOrientation(root.leftChild, subTreeOrientation);
            KDTree rightChild = findMinimumOfOrientation(root.rightChild, subTreeOrientation);

            if (!subTreeOrientation) {
                if (leftChild.bank.x <= rightChild.bank.x) {
                    if (leftChild.bank.x <= root.bank.x) {
                        return leftChild;
                    } else return root;
                } else {
                    if (rightChild.bank.x <= root.bank.x) {
                        return rightChild;
                    } else return root;
                }
            } else {
                if (leftChild.bank.y <= rightChild.bank.y) {
                    if (leftChild.bank.y <= root.bank.y) {
                        return leftChild;
                    } else return root;
                } else {
                    if (rightChild.bank.y <= root.bank.y) {
                        return rightChild;
                    } else return root;
                }
            }
        } else if (root.rightChild != null) {
            KDTree rightChild = findMinimumOfOrientation(root.rightChild, subTreeOrientation);
            if (!subTreeOrientation) {
                if (rightChild.bank.x <= root.bank.x) {
                    return rightChild;
                } else return root;
            } else {
                if (rightChild.bank.y <= root.bank.y) {
                    return rightChild;
                } else return root;
            }
        } else if (root.leftChild != null) {
            KDTree leftChild = findMinimumOfOrientation(root.leftChild, subTreeOrientation);
            if (!subTreeOrientation) {
                if (leftChild.bank.x <= root.bank.x) {
                    return leftChild;
                } else return root;
            } else {
                if (leftChild.bank.y <= root.bank.y) {
                    return leftChild;
                } else return root;
            }
        } else {
            return root;
        }
    }

    public Bank search(KDTree root, double x, double y, boolean orientation) {

        if (root == null) {
            return null;
        }
        if (root.bank.x == x && root.bank.y == y) {
            return root.bank;
        }

        if (!orientation && x < root.bank.x)
            return search(root.leftChild, x, y, !orientation);

        if (!orientation && bank.x >= root.bank.x)
            return search(root.rightChild, x, y, !orientation);

        if (orientation && bank.y < root.bank.y)
            return search(root.leftChild, x, y, !orientation);

        if (orientation && bank.y >= root.bank.y)
            return search(root.rightChild, x, y, !orientation);

        return null;
    }

    public void rangeSearch(KDTree root, boolean orientation, double x1, double y1, double x2, double y2) {

        if (root == null) {
            return;
        }

        if (x1 <= root.bank.x && root.bank.x <= x2 && y1 <= root.bank.y && root.bank.y <= y2) {
            System.out.println(root.bank.name + " -- [" + root.bank.x + "," + root.bank.y + "]");
        }

        if (root.rightChild != null || root.leftChild != null) {
            if (!orientation) {
                if (x1 <= root.bank.x && root.bank.x <= x2) {
                    // har 2 zir derakht bayad begarde
                    if (root.rightChild != null && root.leftChild != null) {
                        rangeSearch(root.rightChild, !orientation, x1, y1, x2, y2);
                        rangeSearch(root.leftChild, !orientation, x1, y1, x2, y2);
                    } else if (root.leftChild != null) {
                        rangeSearch(root.leftChild, !orientation, x1, y1, x2, y2);
                    } else if (root.rightChild != null) {
                        rangeSearch(root.rightChild, !orientation, x1, y1, x2, y2);
                    }
                } else if (root.bank.x >= x2) {
                    if (root.leftChild != null)
                        rangeSearch(root.leftChild, !orientation, x1, y1, x2, y2);

                } else if (root.bank.x <= x1) {
                    if (root.rightChild != null)
                        rangeSearch(root.rightChild, !orientation, x1, y1, x2, y2);
                }
            } else {
                if (y1 <= root.bank.y && root.bank.y <= y2) {
                    // har 2 zir derakht bayad begarde
                    if (root.rightChild != null && root.leftChild != null) {
                        rangeSearch(root.rightChild, !orientation, x1, y1, x2, y2);
                        rangeSearch(root.leftChild, !orientation, x1, y1, x2, y2);
                    } else if (root.leftChild != null) {
                        rangeSearch(root.leftChild, !orientation, x1, y1, x2, y2);
                    } else if (root.rightChild != null) {
                        rangeSearch(root.rightChild, !orientation, x1, y1, x2, y2);
                    }
                } else if (root.bank.y >= y2) {
                    if (root.leftChild != null)
                        rangeSearch(root.leftChild, !orientation, x1, y1, x2, y2);

                } else if (root.bank.y <= y1) {
                    if (root.rightChild != null)
                        rangeSearch(root.rightChild, !orientation, x1, y1, x2, y2);
                }
            }
        } else return;
    }

    public void rangeSearch(KDTree root, boolean orientation, double r, double x, double y) {

        if (root == null) {
            return;
        }

        if ((root.bank.x - x) * (root.bank.x - x) + (root.bank.y - y) * (root.bank.y - y) <= r * r) {
            System.out.println(root.bank.name + " -- [" + root.bank.x + "," + root.bank.y + "]");
        }

        if (root.rightChild != null || root.leftChild != null) {
            if (!orientation) {
                if (x - r <= root.bank.x && root.bank.x <= x + r) {
                    // har 2 zir derakht bayad begarde
                    if (root.rightChild != null && root.leftChild != null) {
                        rangeSearch(root.rightChild, !orientation, r, x, y);
                        rangeSearch(root.leftChild, !orientation, r, x, y);
                    } else if (root.leftChild != null) {
                        rangeSearch(root.leftChild, !orientation, r, x, y);
                    } else if (root.rightChild != null) {
                        rangeSearch(root.rightChild, !orientation, r, x, y);
                    }
                } else if (root.bank.x >= x + r) {
                    if (root.leftChild != null)
                        rangeSearch(root.leftChild, !orientation, r, x, y);

                } else if (root.bank.x <= x - r) {
                    if (root.rightChild != null)
                        rangeSearch(root.rightChild, !orientation, r, x, y);
                }
            } else {
                if (y - r <= root.bank.y && root.bank.y <= y + r) {
                    // har 2 zir derakht bayad begarde
                    if (root.rightChild != null && root.leftChild != null) {
                        rangeSearch(root.rightChild, !orientation, r, x, y);
                        rangeSearch(root.leftChild, !orientation, r, x, y);
                    } else if (root.leftChild != null) {
                        rangeSearch(root.leftChild, !orientation, r, x, y);
                    } else if (root.rightChild != null) {
                        rangeSearch(root.rightChild, !orientation, r, x, y);
                    }
                } else if (root.bank.y >= y + r) {
                    if (root.leftChild != null)
                        rangeSearch(root.leftChild, !orientation, r, x, y);

                } else if (root.bank.y <= y - r) {
                    if (root.rightChild != null)
                        rangeSearch(root.rightChild, !orientation, r, x, y);
                }
            }
        } else return;
    }

    public void inOrderTraverse(KDTree root) {

        if (root == null)
            return;

        inOrderTraverse(root.leftChild);

        System.out.println("[" + root.bank.x + "," + root.bank.y + "]");

        inOrderTraverse(root.rightChild);
    }

    public KDTree nearestNeighbourSearch(KDTree root, double x, double y, boolean orientation, KDTree champion) {

        if (root == null) {
            return null;
        }

        if (distanceSquared(root.bank.x, root.bank.y, x, y) <= distanceSquared(champion.bank.x, champion.bank.y, x, y)) {
            champion = root;
        }

        if (root.rightChild != null || root.leftChild != null) {
            if (!orientation) {
                if (x <= root.bank.x) {
                    if (root.rightChild == null) {
                        champion = nearestNeighbourSearch(root.leftChild, x, y, !orientation, champion);
                    } else if (root.leftChild == null) {
                        if (distanceSquared(root.bank.x, root.bank.y, x, y) < distanceSquared(root.bank.x, root.rightChild.bank.y, x, y)) {
                            champion = nearestNeighbourSearch(root.rightChild, x, y, !orientation, champion);
                        }
                    } else {
                        champion = nearestNeighbourSearch(root.leftChild, x, y, !orientation, champion);
                        if (distanceSquared(champion.bank.x, champion.bank.y, x, y) < distanceSquared(root.bank.x, root.rightChild.bank.y, x, y)) {
                            champion = nearestNeighbourSearch(root.rightChild, x, y, !orientation, champion);
                        }
                    }
                } else {
                    if (root.leftChild == null) {
                        champion = nearestNeighbourSearch(root.rightChild, x, y, !orientation, champion);
                    } else if (root.rightChild == null) {
                        if (distanceSquared(root.bank.x, root.bank.y, x, y) < distanceSquared(root.bank.x, root.leftChild.bank.y, x, y)) {
                            champion = nearestNeighbourSearch(root.leftChild, x, y, !orientation, champion);
                        }
                    } else {
                        champion = nearestNeighbourSearch(root.rightChild, x, y, !orientation, champion);
                        if (distanceSquared(champion.bank.x, champion.bank.y, x, y) < distanceSquared(root.bank.x, root.leftChild.bank.y, x, y)) {
                            champion = nearestNeighbourSearch(root.leftChild, x, y, !orientation, champion);
                        }
                    }
                }
            } else {
                if (y <= root.bank.y) {
                    if (root.rightChild == null) {
                        champion = nearestNeighbourSearch(root.leftChild, x, y, !orientation, champion);
                    } else if (root.leftChild == null) {
                        if (distanceSquared(root.bank.x, root.bank.y, x, y) < distanceSquared(root.rightChild.bank.x, root.bank.y, x, y)) {
                            champion = nearestNeighbourSearch(root.rightChild, x, y, !orientation, champion);
                        }
                    } else {
                        champion = nearestNeighbourSearch(root.leftChild, x, y, !orientation, champion);
                        if (distanceSquared(champion.bank.x, champion.bank.y, x, y) < distanceSquared(root.bank.x, root.rightChild.bank.y, x, y)) {
                            champion = nearestNeighbourSearch(root.rightChild, x, y, !orientation, champion);
                        }
                    }
                } else {
                    if (root.leftChild == null) {
                        champion = nearestNeighbourSearch(root.rightChild, x, y, !orientation, champion);
                    } else if (root.rightChild == null) {
                        if (distanceSquared(root.bank.x, root.bank.y, x, y) < distanceSquared(root.leftChild.bank.x, root.bank.y, x, y)) {
                            champion = nearestNeighbourSearch(root.leftChild, x, y, !orientation, champion);
                        }
                    } else {
                        champion = nearestNeighbourSearch(root.rightChild, x, y, !orientation, champion);
                        if (distanceSquared(champion.bank.x, champion.bank.y, x, y) < distanceSquared(root.bank.x, root.leftChild.bank.y, x, y)) {
                            champion = nearestNeighbourSearch(root.leftChild, x, y, !orientation, champion);
                        }
                    }
                }
            }
        }

        return champion;
    }

    public double distanceSquared(double x1, double y1, double x2, double y2) {
        return ((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
}
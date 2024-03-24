import java.util.*;

public class MultiLinkedList {
    Node head;
    int playerNumber;
    int filledRowCount;

    int filledCellValue = -1;
    int blockedCellValue = -2;

    public MultiLinkedList(int playerNumber) {
        this.head = null;
        this.filledRowCount = 0;
        this.playerNumber = playerNumber;
    }

    public void init() {
        addRows();
        sortRows();
        blockRandomCells();
    }

    private void addRows() {
        for (int rowIndex = 0; rowIndex < 3; rowIndex++) {
            Node newRowNode = new Node(rowIndex);
            if (head == null) {
                head = newRowNode;
            } else {
                Node temp = head;
                while (temp.above != null) {
                    temp = temp.above;
                }
                temp.above = newRowNode;
                newRowNode.below = temp;
            }
            addCells(newRowNode);
        }
    }

    private void addCells(Node rowNode) {
        for (int cellIndex = 0; cellIndex < 9; cellIndex++) {
            int data = getCellData(cellIndex);

            Node newCellNode = new Node(data);
            Node temp = rowNode;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newCellNode;
            newCellNode.prev = temp;
        }
    }

    private int getCellData(int index) {
        Random rand = new Random();
        int randomNumber;
        int min = index == 0 ? 1 : index * 10;
        int max = index == 0 ? 10 : index == 8 ? 91 : (index * 10) + 10;

        do {
            randomNumber = rand.nextInt(max - min) + min;
        } while (this.contains(randomNumber));
        return randomNumber;
    }

    public void sortRows() {
        Node row1 = head;
        if (row1.above != null && row1.above.above != null) {
            Node row2 = row1.above;
            Node row3 = row1.above.above;

            Node row1Cell = row1.next;
            Node row2Cell = row2.next;
            Node row3Cell = row3.next;

            while (row1Cell != null && row2Cell != null && row3Cell != null) {
                if (row3Cell.data < row2Cell.data) {
                    int copyCellData = row2Cell.data;
                    row2Cell.data = row3Cell.data;
                    row3Cell.data = copyCellData;
                }

                if (row2Cell.data < row1Cell.data) {
                    int copyCellData = row1Cell.data;
                    row1Cell.data = row2Cell.data;
                    row2Cell.data = copyCellData;
                }

                if (row3Cell.data < row2Cell.data) {
                    int copyCellData = row2Cell.data;
                    row2Cell.data = row3Cell.data;
                    row3Cell.data = copyCellData;
                }

                row1Cell = row1Cell.next;
                row2Cell = row2Cell.next;
                row3Cell = row3Cell.next;
            }
        }
    }

    private void blockRandomCells() {
        Random rand = new Random();
        Node tempRow = head;
        while (tempRow != null) {
            int blockedCellCount = 0;
            while (blockedCellCount < 4) {
                int blockedIndex = rand.nextInt(9);
                Node tempCell = tempRow.next;
                int cellIndex = 0;
                while (tempCell != null) {
                    if (blockedIndex == cellIndex && tempCell.data != this.blockedCellValue) {
                        tempCell.data = this.blockedCellValue;
                        blockedCellCount++;
                    }
                    tempCell = tempCell.next;
                    cellIndex++;
                }
            }
            tempRow = tempRow.above;
        }
    }

    public void print() {
        System.out.println("--------------------------------");
        System.out.println("Oyuncu " + this.playerNumber + "'in kartı:");
        Node tempRow = head;
        while (tempRow != null) {
            Node tempCell = tempRow.next;
            while (tempCell != null) {
                if (tempCell.data == this.filledCellValue || tempCell.data == this.blockedCellValue) {
                    System.out.print("-- ");
                } else {
                    System.out.print(String.format("%02d", tempCell.data) + " ");
                }
                tempCell = tempCell.next;
            }
            tempRow = tempRow.above;
            System.out.println("");
        }
        System.out.println("--------------------------------");
    }

    public boolean contains(int data) {
        Node tempRow = head;
        while (tempRow != null) {
            Node tempCell = tempRow.next;
            while (tempCell != null) {
                if (tempCell.data == data) {
                    return true;
                }
                tempCell = tempCell.next;
            }

            tempRow = tempRow.above;
        }
        return false;
    }

    public boolean fillNumber(int data) {
        Node tempRow = head;
        while (tempRow != null) {
            Node tempCell = tempRow.next;
            while (tempCell != null) {
                if (tempCell.data == data) {
                    tempCell.data = this.filledCellValue; // Mark the number as used
                    System.out.println("Numara " + data + " Oyuncu " + this.playerNumber + "'in kartında mevcut.");
                    return true;
                }
                tempCell = tempCell.next;
            }

            tempRow = tempRow.above;
        }
        return false;
    }

    public boolean isRowFilled() {
        Node tempRow = head;
        while (tempRow != null) {
            int count = 0;
            Node tempCell = tempRow.next;
            while (!tempRow.isFilled && tempCell != null) {
                if (tempCell.data == this.filledCellValue) {
                    count++;
                }
                tempCell = tempCell.next;
            }

            if (!tempRow.isFilled && count >= 5) {
                tempRow.isFilled = true;
                this.filledRowCount++;
                if (this.filledRowCount == 1) {
                    System.out.println("Oyuncu " + this.playerNumber + " birinci çinkoyu yaptı!");
                } else if (this.filledRowCount == 2) {
                    System.out.println("Oyuncu " + this.playerNumber + " ikinci çinkoyu yaptı!");
                } else if (this.filledRowCount == 3) {
                    System.out.println("Oyuncu " + this.playerNumber + " tombala yaptı ve oyunu kazandı!");
                }
                return true;
            }

            tempRow = tempRow.above;
        }
        return false;
    }
}

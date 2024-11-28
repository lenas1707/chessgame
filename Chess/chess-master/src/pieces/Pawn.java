package pieces;

import utils.PieceColor;
import utils.Position;

public class Pawn extends Piece {
    private boolean hasMoved; // Atributo para rastrear se o peão já se moveu

    public Pawn(PieceColor color, Position position) {
        super(color, position);
        this.hasMoved = false; // Peão criado nunca foi movido
    }

    @Override
    public boolean isValidMove(Position newPosition, Piece[][] board) {
        // Log para depuração
        System.out.println("Validating move for Pawn from " + position + " to " + newPosition);

        if (!isWithinBounds(newPosition)) {
            System.out.println("Move out of bounds!");
            return false;
        }

        int rowDiff = newPosition.getRow() - position.getRow();
        int colDiff = newPosition.getColumn() - position.getColumn();

        // Determinar direção com base na cor
        int forwardDirection = (color == PieceColor.WHITE) ? -1 : 1;

        // Movimento normal (uma casa para frente)
        if (colDiff == 0 && rowDiff == forwardDirection) {
            if (board[newPosition.getRow()][newPosition.getColumn()] == null) {
                return true;
            } else {
                System.out.println("Blocked at: " + newPosition);
                return false;
            }
        }

        // Movimento inicial (duas casas para frente)
        if (!hasMoved && colDiff == 0 && rowDiff == 2 * forwardDirection) {
            int middleRow = position.getRow() + forwardDirection;
            if (board[newPosition.getRow()][newPosition.getColumn()] == null &&
                    board[middleRow][position.getColumn()] == null) {
                return true;
            } else {
                System.out.println("Blocked at: " + newPosition + " or " + new Position(middleRow, position.getColumn()));
                return false;
            }
        }

        // Movimento de captura (diagonal)
        if (Math.abs(colDiff) == 1 && rowDiff == forwardDirection) {
            Piece targetPiece = board[newPosition.getRow()][newPosition.getColumn()];
            if (targetPiece != null && targetPiece.getColor() != this.color) {
                return true;
            } else {
                System.out.println("Invalid capture at: " + newPosition);
                return false;
            }
        }

        // Caso nenhuma condição seja satisfeita, o movimento é inválido
        System.out.println("Invalid move: Not a valid Pawn move");
        return false;
    }


    public void move(Position newPosition) {
        this.position = newPosition;
        this.hasMoved = true; // Marca que o peão já foi movido
    }
}

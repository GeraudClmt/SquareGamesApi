package fr.cnalps.squaregames.request;

import fr.le_campus_numerique.square_games.engine.CellPosition;

public class GameMoveTokenParamsRequest {
    private String tokenName;
    private CellPosition startPosition;
    private CellPosition endPosition;

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public CellPosition getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(CellPosition startPosition) {
        this.startPosition = startPosition;
    }

    public CellPosition getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(CellPosition endPosition) {
        this.endPosition = endPosition;
    }

}

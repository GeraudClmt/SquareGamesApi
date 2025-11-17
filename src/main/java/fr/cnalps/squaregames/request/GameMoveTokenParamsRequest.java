package fr.cnalps.squaregames.request;

import fr.le_campus_numerique.square_games.engine.CellPosition;

public class GameMoveTokenParamsRequest {
    private String tokenId;
    private CellPosition startPosition;
    private CellPosition endPosition;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
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

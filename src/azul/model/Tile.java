/**
 * @date 19/12/2022
 * @brief Les Tile sont l'élément de base du jeu
 * Elles peuvent être de différents types, définis par l'énumération TileType
 */

package azul.model;

public class Tile 
{
	private final TileType type;
	
	/**
	 * Constructeur surchargé avec le type de la Tile
	 * 
	 * @param type Type de la tuile à créer
	 */
	public Tile(TileType type)
	{
		this.type = type;
	}
	
	/**
	 * Accesseur du type de la tuile
	 * 
	 * @return Elément de l'énumération TileType, correspondant au type de la tuile
	 */
	public TileType getType()
	{
		return this.type;
	}
}

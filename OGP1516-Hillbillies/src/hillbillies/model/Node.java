package hillbillies.model;

import java.util.*;

import be.kuleuven.cs.som.annotate.*;

/**
 * A Class of Nodes for use with the A* pathfinding algorithm
 * @author Sander Declercq
 * @author Bram Belpaire
 *
 */
public class Node implements Comparable {

	/**
	 * Initialize a new Node with a given position, G-cost and H-Cost.
	 * @param position
	 * 			The position of this new Node.
	 * @param GCost
	 * 			The G-cost of this new Node.
	 * @param HCost
	 * 			The H-cost of this new Node.
	 * @effect	The position of this new Node is set to the given position.
	 * 			| this.setCubeCoordinates(position)
	 * @effect	The G-cost of this new Node is set to the given G-cost.
	 * 			| this.setGCost(GCost)
	 * @effect	The H-cost of this new Node is set to the given H-cost.
	 * 			| this.setHCost(HCost)
	 * @effect	The F-cost of this new Node is set according to its G-cost and H-cost.
	 * 			| this.setFCost()
	 */
	public Node(Vector position, int GCost, int HCost){
		this.setCubeCoordinates(position);
		this.setGCost(GCost);
		this.setHCost(HCost);
		this.setFCost();
	}

	/**
	 * Initialize a new Node with a given position and no G-cost or H-cost yet
	 * @param position
	 * 			The position of this new Node.
	 * @effect	The position of this new Node is set to the given position.
	 * 			| this.setCubeCoordinates(position)
	 * @effect	The G-cost of this new Node is set to the largest possible value.
	 * 			| this.setGCost(Integer.MAX_VALUE)
	 * @effect	The H-cost of this new Node is set to the largest possible value.
	 * 			| this.setHCost(Integer.MAX_VALUE)
	 * @effect	The F-cost of this new Node is set according to its G-cost and H-cost.
	 * 			| this.setFCost()
	 */
	public Node(Vector position){
		this(position,Integer.MAX_VALUE,Integer.MAX_VALUE);
	}

	/**
	 * Return the CubeCoordinates of this Node.
	 */
	@Basic
	public Vector getCubeCoordinates(){
		return this.cubeCoordinates;
	}

	/**
	 * Set the cube coordinates for this Node to the cube coordinates of the given Vector
	 * @param position
	 * 			The Vector giving the location for this Node.
	 * @post	The cube coordinates of this Node equal the cube coordinates of the given position
	 * 			| new.getCubeCoordinates()[0] == position.getCubeX() &&
	 * 			| new.getCubeCoordinates()[1] == position.getCubeY() &&
	 * 			| new.getCubeCoordinates()[2] == position.getCubeZ()
	 */
	private void setCubeCoordinates(Vector position){
		this.cubeCoordinates = new Vector(position.getCubeX(), position.getCubeY(), position.getCubeZ());
	}

	/**
	 * Variable registering the coordinates of this Node.
	 */
	private Vector cubeCoordinates;

	/**
	 * Return the F-Cost of this Node.
	 */
	public int getFCost(){
		return this.FCost;
	}

	/**
	 * Set the FCost of this Node to the given FCost.
	 * @post	The FCost of this Node equals the sum of its GCost and its HCost.
	 * 			| new.getFCost() == this.getGCost() + this.getHCost()
	 */
	private void setFCost(){
		this.FCost = this.getGCost() + this.getHCost();
	}

	/**
	 * Variable registering the F-Cost of this Node.
	 */
	private int FCost;

	/**
	 * Return the G-Cost of this Node.
	 */
	public int getGCost(){
		return this.GCost;
	}

	/**
	 * Set this Node's GCost to the given
	 * @param 	GCost
	 * 			The new GCost for this Node.
	 * @pre		The given GCost is not negative
	 * 			| GCost >= 0
	 * @post	The Node's GCost equals the given GCost
	 * 			| new.getGCost() == GCost
	 */
	public void setGCost(int GCost){
		assert (GCost >= 0);
		this.GCost = GCost;
	}

	/**
	 * Variable registering the G-Cost of this Node.
	 */
	private int GCost;

	/**
	 * Return the G-Cost of this Node.
	 */
	public int getHCost(){
		return this.HCost;
	}

	/**
	 * Set this Node's HCost to the given
	 * @param 	HCost
	 * 			The new HCost for this Node.
	 * @pre		The given HCost is not negative
	 * 			| HCost >= 0
	 * @post	The Node's HCost equals the given HCost
	 * 			| new.getHCost() == HCost
	 */
	public void setHCost(int HCost){
		assert (HCost >= 0);
		this.HCost = HCost;
	}

	/**
	 * Variable registering the G-Cost of this Node.
	 */
	private int HCost;

	public static int calculateDistance(Node start, Node end){
		int x = (int) Math.abs(end.getCubeCoordinates().getCubeX() - start.getCubeCoordinates().getCubeX());
		int y = (int) Math.abs(end.getCubeCoordinates().getCubeY() - start.getCubeCoordinates().getCubeY());
		int z = (int) Math.abs(end.getCubeCoordinates().getCubeZ() - start.getCubeCoordinates().getCubeZ());
		int distance = 0;
		while ((x > 0) && (y > 0) && (z > 0)){
			distance += 17;
			x -= 1; y -= 1; z -= 1;
		}
		while (((x > 0) && (y > 0)) || ((x > 0) && (z > 0)) || ((y > 0) && (z > 0))){
			distance += 14;
			x -= 1; y -= 1; z -= 1;
		}
		while ((x > 0) || (y > 0) || (z > 0)){
			distance += 10;
			x -= 1; y -= 1; z -= 1;
		}
		return distance;
	}

	/**
	 * Return a set containing all Nodes neighbouring this Node.
	 */
	public Set<Node> getNeighbouringNodes(){
		Set<Node> result = new HashSet<>();
		for (int x = -1; x <= 1; x++){
			for (int y = -1; y <= 1; y++){
				for(int z = -1; z <= 1; z++){
					if ((x != 0) || (y != 0) || (z != 0)){
						Vector neighbourPosition = this.getCubeCoordinates().add(new Vector(x,y,z));
						result.add(new Node(neighbourPosition));
					}
				}
			}
		}
		return result;
	}

	/**
	 * Return a boolean reflecting whether this Node is equal to a given Node.
	 * @param	other
	 * 			The object to check for equality with this Node.
	 * @return	true if the given object is a Node and occupies the same position as this Node.
	 * 			| result == (Node.class.isInstance(other)) && 
	 * 			| 			this.getCubeCoordinates().equals(((Node) other).getCubeCoordinates())
	 */
	@Override
	public boolean equals(Object other){
		if (!Node.class.isInstance(other))
			return false;
		return (this.getCubeCoordinates().equals(((Node) other).getCubeCoordinates()));
	}

	
	@Override
	public int compareTo(Object other) {
		if (!Node.class.isInstance(other))
			throw new IllegalArgumentException();
		if (this.getFCost() < ((Node) other).getFCost())
			return -1;
		else if (this.getFCost() > ((Node) other).getFCost())
			return 1;
		else {
			if (this.getHCost() < ((Node) other).getHCost())
				return -1;
			else if (this.getHCost() > ((Node) other).getHCost())
				return 1;
			else
				return 0;
		}
	}
}
public class Matrix {
	
	private double[][] matrix;
	
	public Matrix(int height, int width){
		this.matrix = new double[height][width];
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++)
				this.matrix[i][j] = 0;
		}
	}
	
	public Matrix(Matrix matrix){
		this.matrix = new double[matrix.getHeight()][matrix.getWidth()];
		for(int i = 0; i < this.getHeight(); i++){
			for(int j = 0; j < this.getWidth(); j++)
				this.setElement(i, j, matrix.getElement(i, j));
		}
	}
	
	public Matrix(double[][] matrix){
		this.matrix = new double[matrix.length][matrix[0].length];
		for(int i = 0; i < this.getHeight(); i++){
			for(int j = 0; j < this.getWidth(); j++)
				this.setElement(i, j, matrix[i][j]);
		}
	}
	
	public double getElement(int i, int j){
		return this.matrix[i][j];
	}
	
	public void setElement(int i, int j, double value){
		this.matrix[i][j] = value;
	}
	
	public int getHeight(){
		return this.matrix.length;
	}
	
	public int getWidth(){
		return this.matrix[0].length;
	}
	
	public Matrix product(Matrix m){
		if(this.getWidth() != m.getHeight())
			throw new IllegalArgumentException("Dimensions must agree, width m1 = " + this.getWidth() + ", height m2 = " + m.getHeight());
		Matrix result = new Matrix(this.getHeight(), m.getWidth());
			for(int i = 0; i < this.getHeight(); i++){
				for(int k = 0; k < m.getWidth(); k++){
					double value = 0;
					for(int j = 0; j < this.getWidth(); j++)
						value += this.getElement(i,j) * m.getElement(j, k);
					result.setElement(i, k, value);
				}
			}
		return result;
	}

	public static Matrix product(Matrix m1, Matrix m2){
		return m1.product(m2);
	}
	
	public Matrix add(Matrix m){
		Matrix result = new Matrix(this.getHeight(), this.getWidth());
		for(int i = 0; i < this.getHeight(); i++){
			for(int j = 0; j < this.getWidth(); j++)
				result.setElement(i, j, this.getElement(i,j) + m.getElement(i, j));
		}
		return result;
	}
	
	public static Matrix add(Matrix m1, Matrix m2){
		return m1.add(m2);
	}
	
	public Matrix subtract(Matrix m){
		Matrix result = new Matrix(this.getHeight(), this.getWidth());
		for(int i = 0; i < this.getHeight(); i++){
			for(int j = 0; j < this.getWidth(); j++)
				result.setElement(i,j, this.getElement(i,j) - m.getElement(i, j));
		}
		return result;
	}

	public static Matrix subtract(Matrix m1, Matrix m2){
		return m1.subtract(m2);
	}
	
	public Matrix multiply(Matrix m){
		Matrix result = new Matrix(this.getHeight(), this.getWidth());
		for(int i = 0; i < this.getHeight(); i++){
			for(int j = 0; j < this.getWidth(); j++)
				result.setElement(i,j, this.getElement(i,j) * m.getElement(i, j));
		}
		return result;
	}

	public static Matrix multiply(Matrix m1, Matrix m2){
		return m1.multiply(m2);
	}
	
	public Matrix divide(Matrix m){
		Matrix result = new Matrix(this.getHeight(), this.getWidth());
		for(int i = 0; i < this.getHeight(); i++){
			for(int j = 0; j < this.getWidth(); j++)
				result.setElement(i,j, this.getElement(i,j) / m.getElement(i, j));
		}
		return result;
	}

	public static Matrix divide(Matrix m1, Matrix m2){
		return m1.divide(m2);
	}
	
	public String toString(){
		String matrix = "";
		for(int i = 0; i < this.getHeight(); i++){
			for(int j = 0; j < this.getWidth(); j++){
				matrix += "" + this.getElement(i,j);
				if(j == this.getWidth() - 1)
					matrix += "\n";
				else
					matrix += " ";
			}
		}
		return matrix;
	}
	
	public static Matrix zeros(int height, int width){
		return new Matrix(height, width);
	}
	
	public static Matrix ones(int height, int width){
		Matrix result = new Matrix(height, width);
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
					result.setElement(i, j, 1);
			}
		}
		return result;
	}
	
	public static Matrix eye(int height, int width){
		Matrix result = new Matrix(height, width);
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				if(i == j)
					result.setElement(i, j, 1);
			}
		}
		return result;
	}

	public Matrix transpose(){
		Matrix result = new Matrix(this.getWidth(), this.getHeight());
		for(int i = 0; i < this.getHeight(); i++){
			for(int j = 0; j < this.getWidth(); j++){
				result.setElement(j, i, this.getElement(i, j));
			}
		}
		return result;
	}
	
	public Matrix subMatrix(int x, int y){
		if(x >= this.getHeight() || y >= this.getWidth())
			throw new IllegalArgumentException("Out of bounds");
		Matrix result = new Matrix(this.getHeight()-1, this.getWidth()-1);
		int correctioni = 0;
		int correctionj = 0;
		for(int i = 0; i < result.getHeight(); i++){
			for(int j = 0; j < result.getWidth(); j++){
				if(i >= x)
					correctioni = 1;
				else if(i < x)
					correctioni = 0;
				if(j >= y)
					correctionj = 1;
				else if(j < y)
					correctionj = 0;
				result.setElement(i, j, this.getElement(i + correctioni, j + correctionj));
			}
		}
		return result;
	}
	
	public double determinant(){
		if(this.getHeight() != this.getWidth())
			throw new IllegalArgumentException("Non quadratic matrix!");
		else if(this.getHeight() == 2 && this.getHeight() == 2){
			return this.getElement(0,0) * this.getElement(1, 1) - this.getElement(0, 1) * this.getElement(1, 0);
		}
		int result = 0;
		for(int i = 0; i < this.getHeight(); i++){
				result += Math.pow(-1, i + 0) * subMatrix(i,0).determinant() * this.getElement(i,0);
		}
		return result;
	}
	
	public static double determinant(Matrix matrix){
		return matrix.determinant();
	}
	
	public Matrix inverse(){
		if(this.determinant() == 0)
			throw new IllegalArgumentException("Non invertable matrix, det(A) = 0");
		Matrix result = new Matrix(this.getHeight(), this.getWidth());
		for(int i = 0; i < result.getHeight(); i++){
			for(int j = 0; j < result.getWidth(); j++){
				result.setElement(i, j, Math.pow(-1, i + j) * this.subMatrix(i, j).determinant() / this.determinant());
			}
		}
		return result.transpose();
	}
	
	public static Matrix inverse(Matrix matrix){
		return matrix.inverse();
	}
	
}

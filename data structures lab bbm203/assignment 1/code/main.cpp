#include <iostream>
#include <fstream>

using namespace std;

int calculator(int** keymatrix, int** mapmatrix, int centerrow, int centercolumn, int keysize){
	int product=0;
	for(int i = 0; i<keysize; i++){
		for(int k = 0; k<keysize; k++){
			product += keymatrix[i][k] * mapmatrix[centerrow-(keysize/2)+i][centercolumn-(keysize/2)+k];
		}
	}
	return product;
}
int movex(int product, int** mapmatrix, int centercolumn,int keysize,int mapcolumnsize){
	// WANT TO GO RIGHT
	if(product % 5 == 3){
		// CHECKING IF IT IS ON RIGHT MOST OR NOT
		if(centercolumn + keysize > mapcolumnsize -1){
			// BOUNCING TO LEFT
			centercolumn -= keysize;
		}
		else{
			// GOING RIGHT
			centercolumn += keysize;
		}
	}
	// WANT TO GO LEFT
	else if(product % 5 == 4){
		// CHECKING IF IT IS ON LEFT MOST OR NOT
		if( centercolumn -keysize <0){
			// BOUNCING TO RIGHT
			centercolumn += keysize;
		}
		else{
			// GOING TO LEFT
			centercolumn -= keysize;
		}
	}
	return centercolumn;
}
int movey(int product, int** mapmatrix, int centerrow,int keysize,int maprowsize){
	// WANT TO GO UP
	if(product%5 == 1){
		// CHECKING IF IT IS ON TOP OR NOT
		if(centerrow-keysize <0){
			// BOUNCING TO DOWN
			centerrow += keysize;
		}
		else{
			// GOING UP
			centerrow -= keysize;
		}
	}
	// WANT TO GO DOWN
	else if(product % 5 == 2){
		// CHECKING IF IT IS ON BOTTOM OR NOT
		if(centerrow + keysize >maprowsize-1){
			// BOUNCING TO UP
			centerrow -= keysize;
		}
		else{
			// GOING DOWN
			centerrow += keysize;
		}
	}
	return centerrow;
}

int main(int argc, char** argv) {
	string str = argv[1];
	string temp ="";
	string tmp = "";
	int keysize = stoi(argv[2]);
	int maprowsize = 0;
	int mapcolumnsize = 0;

	for (int i = 0; i<(int)str.size(); i++){
			tmp = str[i];
			if(tmp != "x"){
				temp += str[i];
				mapcolumnsize = stoi(temp);
			}
			else{
				maprowsize = stoi(temp);
				temp ="";

			}
		}


	int** keymatrix;
	keymatrix = new int*[keysize];
	for(int i = 0;i<keysize;i++){
			keymatrix[i]= new int[keysize];
		}

	int** mapmatrix;
	mapmatrix = new int*[maprowsize];
	for(int i = 0;i<mapcolumnsize;i++){
				mapmatrix[i]= new int[mapcolumnsize];
			}

	// READİNG KEYS
	fstream myfile;
	myfile.open(argv[4], ios::in);

	int ch;
	int i = 0;
	int k = 0;
		while (1) {

			myfile >> ch;
			keymatrix[i][k] = ch;
			k++;
			if (k == keysize){
				k = 0;
				cout<<endl;
				i++;
			}
			if (myfile.eof())
				break;
		}
	myfile.close();




	// READİNG MAP
	fstream my_file2;
	my_file2.open(argv[3], ios::in);
	int ch2;
	i = 0;
	k = 0;
	while (1) {
		my_file2 >> ch2;
		mapmatrix[i][k] = ch2;
		k++;
		if(k == mapcolumnsize){
			k = 0;
			cout<<endl;
			i++;
		}
		if (my_file2.eof())
			break;
	}
	my_file2.close();

	// WRİTİNG OUTPUT
	fstream my_file3;
	my_file3.open(argv[5], ios::out);

	int product = 1;
	int posproduct = 1;
	int centerrow = keysize/2;
	int centercolumn = keysize/2;
	while(product != 0){
		product = calculator(keymatrix,mapmatrix,centerrow,centercolumn,keysize);
		posproduct = product;
		// CONTROLLİNG İF THE SUM IS 5X
		if (product % 5 == 0){
			my_file3 << centerrow << "," << centercolumn << ":" << product << endl;
			break;
		}
		my_file3 << centerrow << "," << centercolumn << ":" << product << endl;
		// IF PRODUCT IS NEGATİVE, ADD 5 TO MAKE IT POSITIVE
		while (true){
			if(posproduct <0){
				posproduct += 5;
			}
			else{
				break;
			}
		}
		// IF MOVE X-AXIS
		if (posproduct % 5 >2){
			centercolumn = movex(posproduct, mapmatrix, centercolumn, keysize, mapcolumnsize);
		}
		// IF MOVE Y-AXIS
		else if (posproduct % 5 < 3){
			centerrow = movey(posproduct, mapmatrix, centerrow, keysize, maprowsize);
		}

	}






	my_file3.close();

	return 0;

}

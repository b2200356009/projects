#include <iostream>
#include <fstream>
using namespace std;

// THIS IS FOR SECONDARY TREE
class secondarynode{
public:
	string name;
	int price;
	secondarynode* left;
	secondarynode* right;
	secondarynode(string name = "",int price = 0, secondarynode* left = NULL,secondarynode* right = NULL):
		name(name),
		price(price),
		left(left),
		right(right)
		{}
};
// AND THIS IS FOR PRIMARY TREE
class primarynode{
public:
	string category;
	primarynode* left;
	primarynode* right;
	secondarynode* item;
	primarynode(string category = "", primarynode* left = NULL, primarynode* right = NULL,secondarynode* item = NULL):
		category(category),
		left(left),
		right(right),
		item(item)
		{}
};
// HERE IS THE AVL TREE
class binarytree{
public:
	primarynode* root;
	// OUTXX IS THE STRING THAT KEEPS OUT INPUT
	string outxx;
	binarytree(primarynode* root = NULL, string outxx = "") : root(root), outxx(outxx){}

// CHECKING IF THE TREE IS EMPTY OR NOT
	bool isempty()const{return root == NULL;}
// FINDING THE HEIGHT OF TREE
	int height(primarynode* node){
		if(node){
			int left = height(node->left);
			int right = height(node->right);
			return 1+max(left,right);
		}
		else
			return 0;
	}
// FINDING THE HEIGHT OF TREE BUT USING SECONDARY NODE
	int heightsec(secondarynode* node){
		if(node){
			int left = heightsec(node->left);
			int right = heightsec(node->right);
			return 1+max(left,right);
		}
		else
			return 0;
	}
// FINDING THE ADRESS OF THE SPESIFIC CATEGORY
	primarynode* findpnode(primarynode* node, string cate){
	    if(node){
	        if(cate < node->category){
	            return findpnode(node->left,cate);
	        }
	        else if(node->category < cate){
	            return findpnode(node->right, cate);
	        }
	    }
	    return node;
	}
//INSERTING A NEW CATEGORY AND ITEM IF IT DOESNT EXIST
	primarynode* insertnon(string cate, primarynode* node){
		if(node){
			if(cate > node->category){
				node->right = insertnon(cate, node->right);
			}
			else if(node->category > cate){
				node->left = insertnon(cate, node->left);
			}
		}
		else{
			return new primarynode(cate);
		}
		return node;
	}
	void insertnon(string cate){
		root = insertnon(cate, root);
	}
// INSERTING NEW ITEM IF CATEGORY ALREADY EXIST
	secondarynode* insertexist(string name, int price, secondarynode* node){
		if(node){
			if(name > node->name){
				node->right = insertexist(name,price,node->right);
			}
			else if(node->name > name){
				node->left = insertexist(name,price,node->left);
			}
		}
		else{
			return new secondarynode(name, price);
		}
		return node;
	}
	void insertexistvoid(string name, int price, secondarynode* root){
		root = insertexist(name,price,root);
	}
// PRINTING TREES LEVEL BY LEVEL
	void printlevelssec(secondarynode* node, int level) {
	  if (node == nullptr) {
		  return;
	  }
	  if (level == 1) {
		  outxx = outxx + "\t" + node->name + ":"+ to_string(node->price);
	  } else {
	    printlevelssec(node->left, level - 1);
	    printlevelssec(node->right, level - 1);
	  }
	}
// TO DETERMINE LEVELS OF TREE
	void printlevels(primarynode* node, int level) {
	  if (node == nullptr) return;
	  if (level == 1) {
		  outxx = outxx + node->category + ":";
		  if(!node->item){
			  outxx = outxx + "{}" + "\n";
			  return;
		  }
		  outxx += "\n";
		  printlevelorder(node->item);
	  } else {
	    printlevels(node->left, level - 1);
	    printlevels(node->right, level - 1);
	  }
	}
// HELPER TO PRINT LEVELS OF TREE
	void printlevelorder(secondarynode* node) {
		if(node){
			int height = heightsec(node);
			for (int i = 1; i <= height; i++) {
				printlevelssec(node, i);
				outxx += "\n";
			}
		}
	}
// PRINTING EVERYTHING
	void printAllItems(primarynode* node){
		outxx = outxx + "{" +"\n";
		if(node){
			int heightx = height(node);
			for(int i = 1; i <= heightx; i++){
				printlevels(node, i);
				outxx = outxx + "\n";
			}
		}
		outxx = outxx + "}" + "\n";

	}
// PRINTING EVERYTHING IN SPESIFIC CATEGORY
	void printAllItemsInCategory(primarynode* node){
		outxx = outxx + "{" + "\n" + node->category + ":";
		if(node->item){
			outxx = outxx + "\n";
			int height = heightsec(node->item);
			for (int i = 1; i <= height; i++) {
				printlevelssec(node->item, i);
				outxx = outxx + "\n";
			}
		}
		else
			outxx = outxx + "{}" + "\n";
		outxx = outxx + "}" + "\n";
	}
//PRINTING INFORMATIONS ABOUT SPESIFIC ITEM
	void printItem(primarynode* node,secondarynode* temp, string name, string cate){
		while(node){
			if(node->category == cate){
				while(temp){
					if(temp->name == name){
						outxx = outxx + "{" + "\n" + node->category + ":\n\t";
						outxx = outxx + temp->name + ":" + to_string(temp->price) + "\n}";
					}
					else if(name < temp->name){
						printItem(node, temp->left,name,cate);
					}
					else if(name > temp->name){
						printItem(node, temp->right,name,cate);
					}
					break;
				}
			}
			else if(cate < node->category){
				printItem(node->left, temp,name,cate);
			}
			else if(cate > node->category){
				printItem(node->right, temp,name,cate);
			}
			break;
		}
	}
// FINDING AND PRINTING INFOS ABOUT SPESIFIC ITEM
	void find(primarynode* node,secondarynode* temp, string name, string cate){
		while(node){
			if(node->category == cate){
				while(temp){
					if(temp->name == name){
						outxx = outxx + "{" + "\n" + node->category + ":\n\t";
						outxx = outxx + temp->name + ":" + to_string(temp->price) + "\n}";

					}
					else if(name < temp->name){
						find(node, temp->left,name,cate);
					}
					else if(name > temp->name){
						find(node, temp->right,name,cate);
					}
					break;
				}
			}
			else if(cate < node->category){
				find(node->left, temp,name,cate);
			}
			else if(cate > node->category){
				find(node->right, temp,name,cate);
			}
			break;
		}
	}

// FINDING THE RECENTLY INSERTED ITEMS ADDRESS TO HELP BALANCING
	secondarynode* findnewnode(secondarynode* temp, string name){
		while(temp){
			if(temp->name == name){
				return temp;
			}
			else if(name < temp->name){
				return findnewnode(temp->left,name);
			}
			else if(name > temp->name){
				return findnewnode(temp->right,name);
			}
			break;
		}
		return temp;
	}
// GETTING THE BALACNCE FACTOR
	int getbalancefactor(secondarynode* node){
		if(node == NULL){
			return -1;
		}
		else
			return (heightsec(node->left)- heightsec(node->right));
	}
	secondarynode* rightrotate(secondarynode* y){
		secondarynode* x = y->left;
		secondarynode* t2 = x->right;
		x->right = y;
		y->left = t2;
		return x;
	}
	secondarynode* leftrotate(secondarynode* x){
		secondarynode* y = x->right;
		secondarynode* t2 = y->left;
		y->left = x;
		x->right = t2;
		return y;
	}

// IF WE NEED TO BALANCE AFTER INSERTION, WE HAVE TO USE THIS ONE
	secondarynode* balanceinsert(secondarynode* newnode, secondarynode* temproot, int bf){
		if(bf > 1 && newnode->name < temproot->left->name){
			return rightrotate(temproot);
		}
		if(bf < -1 && newnode->name > temproot->right->name){
			return leftrotate(temproot);
		}
		if(bf > 1 && newnode->name > temproot->left->name){
			temproot->left = leftrotate(temproot->left);
			return rightrotate(temproot);
		}
		if(bf < -1 && newnode->name < temproot->right->name){
			temproot->right = rightrotate(temproot->right);
			return leftrotate(temproot);
		}
		return temproot;
	}
// GETTING THE LEFTMOST ITEM
	secondarynode* getmin(secondarynode* node){
		while(node && node->left){
			node = node->left;
		}
		return node;
	}
// REMOVING SPESIFIC ITEM
	secondarynode* remove(secondarynode* node, string name){
		if(node){
			if( name < node->name){
				node->left = remove(node->left, name);

			}
			else if(node->name < name){
				node->right = remove(node->right,name);

			}
			else{
				secondarynode* temp = NULL;
				if(node->left && node->right){
					temp = getmin(node->right);
					remove(node,name);
					temp->left = node->left;
					temp->right = node->right;
				}
				else if(node->left){
					temp = node->left;
				}
				else if(node->right){
					temp = node->right;
				}
				return temp;
			}
		return node;
		}
		return node;
	}
// WE USE THIS ONE IF WE WANT TO BALANCE AFTER DELETION
	secondarynode* balancedelete(secondarynode* root, int bf){
		if(bf == 2 && getbalancefactor(root->left) >= 0){
			return rightrotate(root);
		}
		else if(bf == 2 && getbalancefactor(root->left) == -1){
			root->left = leftrotate(root->left);
			return rightrotate(root);
		}
		else if(bf == -1 && getbalancefactor(root->right) <= -0){
			return leftrotate(root);
		}
		else if(bf == -2 && getbalancefactor(root->right) == 1){
			root->right = rightrotate(root->right);
			return leftrotate(root);
		}
		return root;
	}

};


int main(int argc, char** argv) {
	fstream input;
	fstream output;
	input.open(argv[1], ios::in);
	binarytree ptree;

	while(!input.eof()){
		string command;
		input >> command;
		// IF COMMAND IS INSERT THEN WE INSERT
		if(command == "insert"){
			string category;
			string name;
			int price;
			input >> category;
			input >> name;
			input >> price;
			// CHECKING IF THE CATEGORY ALREADY CREATED. IF NOT ENTERS HERE
			if(!ptree.findpnode(ptree.root, category)){

				ptree.insertnon(category);
				ptree.outxx = ptree.outxx + "\n";
				ptree.findpnode(ptree.root, category)->item = new secondarynode(name,price);

			}
			// IF CREATED ENTERS HERE
			else if(ptree.findpnode(ptree.root, category)){
				// INSERTION NEW ITEM
				ptree.insertexistvoid(name, price, ptree.findpnode(ptree.root, category)->item);
				// BALANCING
				int bf;
				bf = ptree.getbalancefactor(ptree.findpnode(ptree.root, category)->item);
				secondarynode* newnode = ptree.findnewnode(ptree.findpnode(ptree.root, category)->item, name);
				secondarynode* temproot = ptree.findpnode(ptree.root, category)->item;
				ptree.findpnode(ptree.root, category)->item = ptree.balanceinsert(newnode, temproot, bf);
			}


		}
		else if(command == "printAllItems"){

			ptree.outxx = ptree.outxx + "command:printAllItems\n";
			ptree.printAllItems(ptree.root);

		}
		else if(command == "remove"){

			string category;
			string name;
			input >> category;
			input >> name;
			// REMOVE AND BALANCE
			int bf;
			bf = ptree.getbalancefactor(ptree.findpnode(ptree.root, category)->item);
			ptree.findpnode(ptree.root, category)->item = ptree.remove(ptree.findpnode(ptree.root, category)->item, name);
			ptree.balancedelete(ptree.findpnode(ptree.root, category)->item, bf);
		}
		else if(command == "printAllItemsInCategory"){
			string category;
			input >> category;
			ptree.outxx = ptree.outxx + "command:printAllItemsInCategory\t" + category + "\n";
			ptree.printAllItemsInCategory(ptree.findpnode(ptree.root, category));
		}
		else if(command == "printItem"){
			string category;
			string name;
			input >> category;
			input >> name;
			ptree.outxx = ptree.outxx + "command:printItem\t" + category + "\t" + name + "\n";
			primarynode* foundNode = ptree.findpnode(ptree.root, category);
			if(foundNode){
				ptree.printItem(ptree.root, ptree.findpnode(ptree.root, category)->item, name, category);
			}
			else{
				ptree.outxx = ptree.outxx +  "{}\n";
			}
		}
		else if(command == "find"){
			string category;
			string name;
			input >> category;
			input >> name;
			ptree.outxx = ptree.outxx + "\ncommand:find\t" + category + "\t" + name + "\n";
			// CHECKING IF THERE IS A CATEGORY EXIST OR NOT
			primarynode* foundnode = ptree.findpnode(ptree.root, category);
			if(foundnode){
				ptree.printItem(ptree.root, ptree.findpnode(ptree.root, category)->item, name, category);
			}
			else{
				ptree.outxx = ptree.outxx + "{}\n";
			}


		}

		else if(command == "updateData"){
			string category;
			string name;
			int newprice;
			input >> category;
			input >> name;
			input >> newprice;
			secondarynode* temp = ptree.findpnode(ptree.root, category)->item;

			while(temp){
				if(temp->name == name){
					temp->price = newprice;
					break;
				}
				else if(name > temp->name){
					temp = temp->right;
				}
				else if(name < temp->name){
					temp = temp->left;
				}
			}


		}
	}
	output.open(argv[2], ios::out);
	fstream output2;
	output2.open(argv[3], ios::out);
	output2 << ptree.outxx;
	output << ptree.outxx;
	output.close();
	output2.close();

	input.close();
	return 0;
}

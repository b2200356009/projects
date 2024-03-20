#include <iostream>
#include <fstream>

using namespace std;

// APARTMENT
struct nodeapt{
	string apartment_Name;
	int max_bandwidth;
	int flatnumber;
	int bandwidth;
	struct nodeapt *nextflat;
	struct nodeapt *prevflat;
	struct nodeapt *flat;
	struct nodeapt *nextapartment;
};

typedef struct nodeapt* aptptr;

nodeapt *head = NULL;

aptptr getapt(){
	aptptr p;
	p = new nodeapt;
	return p;
}

// USING THIS TO CALCULATE THE SUM OF BANDWİDTHS OF APARTMENTS
int sumofbandwidth(int counter){
	int bandcounter = 0;
	int sum = 0;
	nodeapt a;
	a.nextapartment = head;
	string temp;
	temp = head->apartment_Name;
	while(a.nextapartment != NULL){
		sum += a.nextapartment->max_bandwidth;
		a.nextapartment = a.nextapartment->nextapartment;
		bandcounter += 1;
		if(bandcounter == counter){
			break;
		}
	}
	return sum;
}


// ADDING APARTMENT TO OUR LINKED LIST
void addapartment(string name, string position, int bandwidth){
	aptptr q;
	// WE USE HERE IF WE WANT TO ADD APARTMENT AFTER A SPESIFIC APARTMENT
	if(position.substr(0,1) == "a"){
		string afterapt;
		afterapt = position.substr(6,7);
		nodeapt* current = head;
		while ( current != NULL){
			if(current->apartment_Name == afterapt){
				q = getapt();
				q->apartment_Name = name;
				q->max_bandwidth = bandwidth;
				q->flat = NULL;
				q->nextapartment = current->nextapartment;
				current->nextapartment = q;

				break;
			}
			current = current->nextapartment;

		}
	}
	// WE USE THIS ONE IF WE WANT TO ADD APARTMENT BEFORE A SPESIFIC APARTMENT
	else if(position.substr(0,1) == "b"){
		string beforeapt;
		beforeapt = position.substr(7,8);
		nodeapt* current = head;
		while (current != NULL){
			//IF WE ADD BETWEEN TWO APARTMENT
			if(current->apartment_Name == beforeapt){
				q = getapt();
				q->apartment_Name = name;
				q->max_bandwidth = bandwidth;
				q->flat = NULL;
				q->nextapartment = current;
				current->nextapartment = q;
				head = q;
				break;
			}
			//IF WE ADD AT THE BEGINNING OF THE LINKED LIST
			else if(current->nextapartment->apartment_Name == beforeapt){
				q = getapt();
				q->apartment_Name = name;
				q->max_bandwidth = bandwidth;
				q->flat = NULL;
				q->nextapartment = current->nextapartment;
				current->nextapartment = q;
				break;
			}

			current = current->nextapartment;
		}

	}
	// IF WE ADD FIRST APARTMENT
	else{
		q = getapt();
		q->apartment_Name = name;
		q->max_bandwidth = bandwidth;
		q->flat = NULL;
		q->nextapartment = head;
		head = q;
	}

}

//REMOVING THE APARTMENT FROM LINKED LIST
void freeapartment(string name){
	aptptr q;
	aptptr temp;
	q = head;
	while(true){


		if(q->nextapartment->apartment_Name == name){
			temp = q->nextapartment;
			q->nextapartment = q->nextapartment->nextapartment;
			delete temp;
			break;
		}
		else{
			q = q->nextapartment;
		}
	}
}

//SHOWING INFORMATIONS ABOUT APARTMENTS
void listapartment(int counter){
	int listcounter = 0;
	aptptr a;
	aptptr firstflat;
	a->nextapartment = head;
	string temp;
	temp = head->apartment_Name;
	while(a->nextapartment != NULL){
		cout<<a->nextapartment->apartment_Name<<"("<<a->nextapartment->max_bandwidth<<")";

		firstflat = a->nextapartment->flat;

		while(firstflat !=NULL){
			cout<<"\t"<<"Flat"<<firstflat->flatnumber<<"("<<firstflat->bandwidth<<")";
			if (firstflat->nextflat->flatnumber != 0){
				break;
			}
			else{
				break;
			}
		}


		a->nextapartment = a->nextapartment->nextapartment;
		listcounter += 1;
		if(listcounter == counter){
			break;
		}
	cout<<endl;
	}

}

// ADDING FLAT
void addflat(string apartmentname, int index, int bandwidth, int flatnumber,int counter){
	aptptr q;
	aptptr newflat;
	newflat = getapt();
	newflat->bandwidth = bandwidth;
	newflat->flatnumber = flatnumber;
	q = head;
	int aptcounter = 0;
	while (aptcounter != counter){
		if(q->apartment_Name == apartmentname){
			if(index == 0){
				if(q->flat != NULL){
					newflat->nextflat = q->flat;
					newflat->nextflat->prevflat = newflat;
					q->flat = newflat;
				}
				else if(q->flat == NULL){
					q->flat = newflat;
				}
			}
			else if(index == 1){
				newflat->nextflat = q->flat->nextflat;
				newflat->prevflat = q->flat;
				q->flat->nextflat = newflat;
				newflat->nextflat->prevflat = newflat;

			}
		}
		else{
			q = q->nextapartment;
		}
	aptcounter +=1;
	}

}

int main(int argc, char** argv) {
	int counter = 0;
	fstream input;
	fstream output;
	input.open(argv[1], ios::in);
	output.open(argv[2],ios::out);
		while(1){
			string command;
			input >> command;
			if (command =="add_apartment"){
				counter += 1;
				string name;
				input >> name;
				string position;
				input >> position;
				int bandwidth;
				input >> bandwidth;
				addapartment(name, position, bandwidth);
			}
			else if (command =="add_flat"){
				string apartmentname;
				input >> apartmentname;
				int index;
				input >> index;
				int bandwidth;
				input >> bandwidth;
				int flatnumber;
				input >> flatnumber;
				addflat(apartmentname, index,bandwidth, flatnumber,counter);


			}
			else if (command =="remove_apartment"){
				counter -= 1;
				string name;
				input >> name;
				freeapartment(name);

			}
			else if (command =="make_flat_empty"){
			}
			else if (command =="find_sum_of_max_bandwidths"){
				cout<<"sum of bandwidth: "<<sumofbandwidth(counter);
				cout<<"\n";
				output<<"sum of bandwidth: "<<sumofbandwidth(counter);
				output<<"\n";
			}
			else if (command =="merge_two_apartments"){
			}
			else if (command =="relocate_flats_to_same_apartment"){
			}
			else if (command =="list_apartments"){
				listapartment(counter);
				cout<<"\n";
				output<<"\n";

			}
			else if (input.eof())
				break;

		}
	input.close();
	output.close();
	return 0;
}

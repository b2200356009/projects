#include <iostream>
#include <fstream>

using namespace std;

struct cashier{
	bool isavaible;
	int cusid;
	double emptytime = 999.02;
	double busytime;
};

struct barista{
	bool isavaible = true;
	int cusid;
	double emptytime= 998.34;
	double busytime;
};


struct customer{
	int id;
	double arrivaltime;
	double ordertime;
	double brewtime;
	double price;
	double tat;
};

int front = -1;
int rear = -1;
int frontc = -1;
int rearc = -1;
// PUTTING A CUSTOMER TO QUEUE
void enqueue(customer cust, customer queue[], int size){
   if(rear == size-1){
	   return;
   }
   if(front==-1){
     front++;
   }
   queue[++rear]=cust;
}
void enqueuec(customer cust, customer queue[], int size){
   if(rearc == size-1){
	   return;
   }
   if(frontc==-1){
     frontc++;
   }
   queue[++rearc]=cust;
}
// TAKING OUT A CUSTOMER FROM QUEUE
customer dequeuec(customer queue[]){
   if(frontc == -1)
      return queue[frontc+1]; //empty
   if(frontc == rearc){
      customer temp = queue[frontc];
      frontc = rearc = -1;
      return temp;
   }
   return queue[frontc++];
}

customer newqueue(customer queue[], int id, int size){
	customer tempqueue[size];
	int x = front;
	while(queue[x].price > 0 && x < size){
		if(queue[x].id != id){
			tempqueue[x].arrivaltime = queue[x].arrivaltime;
			tempqueue[x].brewtime = queue[x].brewtime;
			tempqueue[x].id = queue[x].id;
			tempqueue[x].ordertime = queue[x].ordertime;
			tempqueue[x].price = queue[x].price;
			x += 1;
		}
		else if(queue[x].id == id){
			while(x<size){
				x+=1;
				if(queue[x].id != id){
					tempqueue[x-1].arrivaltime = queue[x].arrivaltime;
					tempqueue[x-1].brewtime = queue[x].brewtime;
					tempqueue[x-1].id = queue[x].id;
					tempqueue[x-1].ordertime = queue[x].ordertime;
					tempqueue[x-1].price = queue[x].price;
					continue;
				}
			break;
			}
		}
	}
	x = front;
	while(x<size){
		queue[x].arrivaltime = tempqueue[x].arrivaltime;
		queue[x].brewtime = tempqueue[x].brewtime;
		queue[x].id =tempqueue[x].id;
		queue[x].ordertime = tempqueue[x].ordertime;
		queue[x].price =tempqueue[x].price;
		x++;
	}
	rear -= 1;
	return queue[front];
}
// DISPLAYING SPESIFIC FEATURE'S OF CUSTOMER'S IN QUEUE
void display(customer queue[]){
	int i;
	if(front == -1){
		cout<<"queue bos"<<endl;
		return;
	}
	for (i = front; i<= rear;i++){
		cout<<queue[i].price<<endl;
	}
}
void displayc(customer queue[]){
	int i;
	if(frontc == -1){
		cout<<"queue bos"<<endl;
		return;
	}
	for (i = frontc; i<= rearc;i++){
		cout<<queue[i].arrivaltime<<endl;
	}
}

double nexttimex(double time1, double time2){
	if(time1 >= time2){
		return time2;
	}
	else{
		return time1;
	}
}

int main(int argc, char** argv) {

	fstream input;
	fstream output;

	input.open(argv[1], ios::in);
	output.open(argv[2], ios::out);

	int numofcashier;
	input >> numofcashier;
	int numoforder;
	input >> numoforder;
	customer allcustomer[numoforder];
	customer cashierq[numoforder];
	customer baristaq[numoforder];
	cashier carray[numofcashier];
	barista barray[numofcashier/3];

	for (int i = 0;i<numofcashier;i++){
		carray[i].busytime = 0;
		carray[i].emptytime = 0;
		carray[i].isavaible = true;
	}
	for (int i = 0;i<numofcashier/3;i++){
		barray[i].busytime = 0;
		barray[i].emptytime = 0;
		barray[i].isavaible = true;
	}

	for(int i = 0; i<numoforder; i++){
		double arrivaltime;
		double totaltime = arrivaltime;
		double ordertime;
		double brewtime;
		double price;
		input >> arrivaltime;
		input >> ordertime;
		input >> brewtime;
		input >> price;
		allcustomer[i].arrivaltime = arrivaltime;
		allcustomer[i].ordertime = ordertime;
		allcustomer[i].brewtime = brewtime;
		allcustomer[i].price = price;
		allcustomer[i].id = i;
		allcustomer[i].tat = allcustomer[i].ordertime+allcustomer[i].brewtime;
	}

	double time = 0;
	int cusid = 0;
	carray[0].cusid = 0;
	int customercounter= 1;

	for(int i = 0;i<numofcashier;i++){
		carray[i].emptytime = 999.23;
		carray[i].busytime = 0;
	}
	for(int i = 0;i<numofcashier/3;i++){
		barray[i].emptytime = 999.27;
		barray[i].busytime = 0;

	}



	int ct = 0;
	while(ct<10){
		double nexttime;

//*****************************************************************************
		// LOOP LEVEL 11111111111111
		//BARISTALARA TEK TEK BAKIYORUZ
		for(int barid = 0;barid < numofcashier/3; barid++){
			// BARISTANIN ISI BITTIYSE MUSTRIYI GONDERECEK
			if (barray[barid].emptytime == time){
				barray[barid].isavaible = true;

				break;
			}

		}
//*****************************************************************************
		// LOOP LEVEL 22222222222222
		// BARISTA SIRASINDA BIRISI VARSA BURAYA GIRER

		if(front != -1){
			int nextcustid = 0;
			for(int i = front;i < rear - front + 1;i++){
				// SIRADA BIR KISI VARSA DIREKT ONU ALIR
				if(front == rear){
					nextcustid = baristaq[i].id;
				}
				else if(rear - front == 1){
					if(baristaq[i].price>baristaq[i+1].price){
						nextcustid = baristaq[i].id;
					}
					else{
						nextcustid = baristaq[i+1].id;
					}

				}
				// BIRDEN FAZLAUSA EN PAHALI OLANI SECER
				else{
					if(baristaq[nextcustid].price > baristaq[i+1].price){
						nextcustid = baristaq[nextcustid].id;
					}
					else{
						nextcustid = baristaq[i+1].id;
					}
				}
			}
			// BOSTA BARISTA VAR MI DIYE BAKIYORUZ
			for(int barid = 0; barid < numofcashier/3; barid++){
				// BOSTA BARISTA VARSA SIRADAKİ MUSTERIYI BARISTAYA GONDER
				if(barray[barid].isavaible == true){
					barray[barid].busytime += allcustomer[nextcustid].brewtime;
					barray[barid].emptytime = time + allcustomer[nextcustid].brewtime;
					barray[barid].isavaible = false;
					newqueue(baristaq, nextcustid, numoforder);
					break;
				}
			}
		}
//*****************************************************************************
		// LOOP LEVEL 3333333333333333333333
		//KASIYERDE ISI BITENI BARISTA SIRASINA YOLLADIK

		for( int cashid = 0; cashid < numofcashier ; cashid++){
			if(carray[cashid].emptytime == time){
				enqueue(allcustomer[carray[cashid].cusid],baristaq,numoforder);

				carray[cashid].isavaible = true;
				break;
			}
		}
		if(front != -1){
			int nextcustid = 0;
			for(int i = front;i < rear - front + 1;i++){
				// SIRADA BIR KISI VARSA DIREKT ONU ALIR
				if(front == rear){
					nextcustid = baristaq[i].id;
				}
				else if(rear - front == 1){
					if(baristaq[i].price>baristaq[i+1].price){
						nextcustid = baristaq[i].id;
					}
					else{
						nextcustid = baristaq[i+1].id;
					}
				}
				// BIRDEN FAZLAUSA EN PAHALI OLANI SECER
				else{
					if(baristaq[nextcustid].price > baristaq[i+1].price){
						nextcustid = baristaq[nextcustid].id;
					}
					else{
						nextcustid = baristaq[i+1].id;
					}
				}
			}
			// BOSTA BARISTA VAR MI DIYE BAKIYORUZ
			for(int barid = 0; barid < numofcashier/3; barid++){
				// BOSTA BARISTA VARSA SIRADAKİ MUSTERIYI BARISTAYA GONDER
				if(barray[barid].isavaible == true){
					barray[barid].busytime += allcustomer[nextcustid].brewtime;
					barray[barid].emptytime = time + allcustomer[nextcustid].brewtime;
					barray[barid].isavaible = false;
					break;
				}
			}
		}
//*****************************************************************************
		// LOOP LEVEL 44444444444444444444444
		// KASIYER SIRASI VARSA VE BOSTA KASIYER VARSA SIRADAKILERI KASIYERE YOLLA
		if(frontc != -1){
			for( int cashid = 0; cashid < numofcashier ; cashid++){
				if(carray[cashid].isavaible == true){
					carray[cashid].busytime += cashierq[frontc].ordertime;
					carray[cashid].cusid = cashierq[frontc].id;
					carray[cashid].emptytime = time + cashierq[frontc].ordertime;
					carray[cashid].isavaible = false;
					dequeuec(cashierq);
				}
			}
		}
		for( int cashid = 0; cashid < numofcashier ; cashid++){
			if(carray[cashid].emptytime == time){
				enqueue(allcustomer[carray[cashid].cusid],baristaq,numoforder);
				carray[cashid].isavaible = true;
				break;
			}
		}
		if(front != -1){
			int nextcustid = 0;
			for(int i = front;i < rear - front + 1;i++){
				// SIRADA BIR KISI VARSA DIREKT ONU ALIR
				if(front == rear){
					nextcustid = baristaq[i].id;
				}
				else if(rear - front == 1){
					if(baristaq[i].price>baristaq[i+1].price){
						nextcustid = baristaq[i].id;
					}
					else{
						nextcustid = baristaq[i+1].id;
					}
				}
				// BIRDEN FAZLAUSA EN PAHALI OLANI SECER
				else{
					if(baristaq[nextcustid].price > baristaq[i+1].price){
						nextcustid = baristaq[nextcustid].id;
					}
					else{
						nextcustid = baristaq[i+1].id;
					}
				}
			}
			// BOSTA BARISTA VAR MI DIYE BAKIYORUZ
			for(int barid = 0; barid < numofcashier/3; barid++){
				// BOSTA BARISTA VARSA SIRADAKİ MUSTERIYI BARISTAYA GONDER
				if(barray[barid].isavaible == true){
					barray[barid].busytime += allcustomer[nextcustid].brewtime;
					barray[barid].emptytime = time + allcustomer[nextcustid].brewtime;
					barray[barid].isavaible = false;
					break;
				}
			}
		}
//*****************************************************************************
		// LOOP LEVEL 5555555555555555555555

		for(int i = 0; i<numoforder;i++){
			if(allcustomer[i].arrivaltime == time){

				enqueuec(allcustomer[i], cashierq, numoforder);

			}
		}
		if(frontc != -1){
			for( int cashid = 0; cashid < numofcashier ; cashid++){
				if(carray[cashid].isavaible == true){
					carray[cashid].busytime += cashierq[frontc].ordertime;
					carray[cashid].cusid = cashierq[frontc].id;
					carray[cashid].emptytime = time + cashierq[frontc].ordertime;
					carray[cashid].isavaible = false;
					dequeuec(cashierq);
					break;

				}
			}
		}
		for( int cashid = 0; cashid < numofcashier ; cashid++){
			if(carray[cashid].emptytime == time){
				enqueue(allcustomer[carray[cashid].cusid],baristaq,numoforder);
				carray[cashid].isavaible = true;
				break;
			}
		}
		if(front != -1){
			int nextcustid = 0;
			for(int i = front;i < rear - front + 1;i++){
				// SIRADA BIR KISI VARSA DIREKT ONU ALIR
				if(front == rear){
					nextcustid = baristaq[i].id;
				}
				else if(rear - front == 1){
					if(baristaq[i].price>baristaq[i+1].price){
						nextcustid = baristaq[i].id;
					}
					else{
						nextcustid = baristaq[i+1].id;
					}
				}
				// BIRDEN FAZLAUSA EN PAHALI OLANI SECER
				else{
					if(baristaq[nextcustid].price > baristaq[i+1].price){
						nextcustid = baristaq[nextcustid].id;
					}
					else{
						nextcustid = baristaq[i+1].id;
					}
				}
			}
			// BOSTA BARISTA VAR MI DIYE BAKIYORUZ
			for(int barid = 0; barid < numofcashier/3; barid++){
				// BOSTA BARISTA VARSA SIRADAKİ MUSTERIYI BARISTAYA GONDER
				if(barray[barid].isavaible == true){
					barray[barid].busytime += allcustomer[nextcustid].brewtime;
					barray[barid].emptytime = time + allcustomer[nextcustid].brewtime;
					barray[barid].isavaible = false;
					break;
				}
			}
		}
//*****************************************************************************
		for (int i = 0; i<numofcashier;i++){
			if(carray[i].emptytime != 0){
				nexttime = nexttimex(carray[i].emptytime,nexttime);
			}

			for(int k = 0; k<numofcashier/3;k++){
				nexttime = carray[0].emptytime;
				if(barray[k].emptytime != 0){
					nexttime = nexttimex(barray[k].emptytime,nexttime);
				}
			}
		}
		if (customercounter < numoforder){
			for(int t = customercounter;t < numoforder; t++){
				nexttime = nexttimex(allcustomer[t].arrivaltime,nexttime);
				if(nexttime == allcustomer[t].arrivaltime&&t<numoforder){
					customercounter++;
				}
			}
		}
		cout<<time<<"----"<<nexttime<<endl;
		time = nexttime;
		cout<<time<<"----"<<nexttime<<endl;
		cout<<"------------"<<endl;


		ct++;
	}
	for(int i = 0;i<numofcashier;i++){
		output<<carray[i].busytime/137.52<<endl;
		cout<<carray[i].busytime/137.52<<endl;
	}
	for(int i = 0;i<numofcashier/3;i++){
		output<<barray[i].busytime/137.52<<endl;
		cout<<barray[i].busytime/137.52<<endl;


	}
	for(int i = 0;i<numoforder; i++){
		output<<allcustomer[i].tat<<endl;
		cout<<allcustomer[i].tat<<endl;

	}
	input.close();
	output.close();

	return 0;
}


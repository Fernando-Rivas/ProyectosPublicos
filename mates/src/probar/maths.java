package probar;

public class maths {
	//////////////////////////////////////Nums////////////////////////////////
	/*Nums es mi buscador de numeros.
	 * Usa la posicion que le des (que por lo general es el indexOf de un operando que vayas a ejecutar.
	 * Devuelve dos numeros y sus posiciones de comienzo y fin correspondientes.
	 */
	public static double[] Nums(String op,int pos) {
		int cont = 0;
		double[] retornar= new double[5];
		boolean buscando = true;
		/* Aqui tengo dos bucles muy parecidos, uno que recorra desde el simbolo hacia 
		 * alante y otro que haga lo mismo hacia atras. Me ira contando cuantos caracteres
		 * cogerme siempre que encuentre o un numero o un "." (aprovechandome del metodo de String matches
		 * para no repetirme con los numeros). 
		 * Una vez encuentre un simbolo,parentesis o fin de linea me devuelve el substring desde el origen
		 * mas los caracteres contados en el cont en la direccion correspondiente
		 * 
		 */
		for(int i = 0;i<pos;i++) {
			while(buscando) {					
			if(op.substring(pos-(i+1),pos-i).matches("0|1|2|3|4|5|6|7|8|9|")||op.substring(pos-(i+1),pos-i).equals(".")) {
				cont++;
				i++;
			}else {
				buscando = false;
				
				}	
			}
	}
		
		retornar[0] = Double.parseDouble(op.substring(pos-cont,pos));
		
		retornar[1]=pos;
		if(op.substring(pos-(1+cont),pos-cont).equals("(")) {
			retornar[2] = 1;
			
		}
		cont = 0;		
		buscando = true;
		for(int i = pos+1;i<op.length();i++) {			
			while(buscando) {				
			if(op.substring(i,i+1).matches("0|1|2|3|4|5|6|7|8|9")||op.substring(i,i+1).equals(".")) {
				cont++;
				i++;
			}else {
				buscando = false;
				}	
			}
	}
		retornar[3] = Double.parseDouble(op.substring(pos+1,pos+1+cont));
		retornar[4] = pos+1+cont;
	return retornar;
	}
	
	
	///////////////////////////////////////////Evaluar contenidos//////////////////////
	public static String eval(String op) {
		//El boolean lo uso para que siga buscando hasta que no queden operaciones, resultados es la devolucion que usare al final
		boolean quedanOperaciones =true;
		double[] resultados = new double[5];
		
		while(quedanOperaciones) {	
			//Siempre que comienza el programa quedanOperaciones empieza como falso
			//Ademas vacia varias variables 
			quedanOperaciones = false;
			resultados[2] = 0;
			int posComienzo=0;
			int posFinal = 0;
			String res = "";
			
			
			
			//Operaciones dos numeros
			/*Aqui buscara una operacion en el orden correspondiente a el orden BIDMAS
			 * (Brackets(parentesis), indices(elevaciones,raices),Division,Multiplicacion,adicion(suma) y substraccion(resta)
			 * Si entra en cualquiera de los ifs significa que ha encontrado una operacion,
			 * Hara que quedanOperaciones sea true y ejecutara Nums seguido por la operacion correspondiente.
			 * El metodo Nums nos proporciona con los numeros adecuados y sus posiciones que se guarda en
			 * el array resultados
			 */
			
			
			if(op.indexOf("/")!=-1) {
				quedanOperaciones = true;			
				resultados = Nums(op,op.indexOf("/"));
				/*En resultados tenemos:
				 * 0: numero 1
				 * 1: posicion del numero 1 (comienzo)
				 * 2: posicion del numero 1 (fin)
				 * 3: numero 2
				 * 4: posicion del numero 2 (fin) 
				 */
				res = Double.toString(resultados[0] / resultados[3]);	
				
			}else if(op.indexOf("*")!=-1) {
				quedanOperaciones = true;						
				resultados = Nums(op,op.indexOf("*"));					
				res = Double.toString(resultados[0] * resultados[3]);
				
			}else if(op.indexOf("+")!=-1) {
				quedanOperaciones = true;
				resultados = Nums(op,op.indexOf("+"));				
				double result = resultados[0]+resultados[3];
				res = Double.toString(result);
				//Uso r en vez de - para la resta para que distinga entre numero negativo y operando de resta
			}else if(op.indexOf("r")!=-1&&op.indexOf("r")!=1) {
				quedanOperaciones = true;				
				resultados = Nums(op,op.indexOf("r"));				
				res = Double.toString(resultados[0] - resultados[3]);				
			}	
			//Si quedan operaciones se sustituye el resultado en su posicion correcta y se vuelve a ejecutar el programa
			if(quedanOperaciones) {	
				if((int)resultados[1]-(Double.toString(resultados[0]).length())==-1 && resultados[2]==1) {
					op = "("+res+op.substring((int)resultados[4]);
				}else {				
				op=op.substring(0,(int)resultados[1]-(Double.toString(resultados[0]).length()))+res+op.substring((int)resultados[4]);
			}
			
			}
			
			
		}	
		//Una vez completado la evaluacion se quitan los parentesis y se retorna el string final
		op = op.substring(1,op.length()-1);		
		return(op);
	}
	////////////////////////Recoger Parentesis///////////////////////////
	/*Recoger parentesis utiliza un string de entrada y te encuentra una pareja de parentesis
	 * que no tengan otros parentesis dentro. Esto es debido a que el primer cierre de parentesis
	 * que encuentre siempre sera uno que no tenga otros parentesis en su interior.
	 * Nos devuelve un array con:
	 * 	-Posicion de apertura
	 * 	-Posicion de cierre
	 * 	-El parentesis y su contenido eg. de (3+2)*3 tendriamos
	 *  Posicion:0, Posicion:4 y contenido: (3+2)
	 */
	public static String[] recogerParentesis(String entrada) {
		String op[] = new String[3];
		op[1] = Integer.toString(entrada.indexOf(")"));
		for(int i = entrada.indexOf(")")-1;i>-1;i--) {				
			if(entrada.substring(i,i+1).equals("(")){
				op[0] = (entrada.substring(i,entrada.indexOf(")")+1));	
				op[2] = Integer.toString(i);
				i = -1;				
				
			}
		}
		return op;
		
	}	
	
	////////////////////////////////////////////MAIN/////////////////////////////////////
	
	public static void main(String[] args) {
		/*Esta es una equacion de ejemplo:
		 * No controla fallos, por ejemplo que falten cierres o aperturas de parentesis o que sobren operaciones
		 */
		String op = "((3+2.5)*(5.25r2)r50)/8.7*9";
		
		//Finalizado lo uso para ver si me faltan operaciones por completar en la equacion
		boolean NoFinalizado = true;
		
		while(NoFinalizado) {
			String tempOP = op;//Guardo la equacion de ahora en un temporal para luego
			System.out.println(op);//El s.out solo esa para visualizar el proceso no es necesario
			/*
			 * Aqui buscara un cierre de parentesis y si lo encuentra evaluara la equacion que haya entre
			 * ese cierre de parentesis y su apertura correspondiente.
			 * Si no la encuentra evaluara la equacion entera
			 */
			if(op.indexOf(")")!=-1){
				op = op.substring(0,Integer.parseInt(recogerParentesis(op)[2]))+eval(recogerParentesis(op)[0])+op.substring(Integer.parseInt(recogerParentesis(op)[1])+1);
			}else {							 
				op = eval("("+op+")");
				
			}
			/*Una vez completada la evaluacion se sustituye lo operado por el resultado. 
			 * Despues comparo si son iguales para ver si deberia continuar el programa.
			 * Si son iguales el temporal anterior con el resultado significa que no se
			 * a operado sobre el por lo que ya no quedan operaciones por hacer.
			*/
			if(tempOP.equals(op)) {
				NoFinalizado = false;
			}
		}
	}
}
	
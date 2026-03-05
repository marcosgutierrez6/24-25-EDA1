import java.util.Random;

public class Fila {

    private Cliente[] clientes;
    private int posicionUltimo;
    private final Random random;

    public Fila(){
        clientes = new Cliente[200];
        posicionUltimo = 0;
        random = new Random();
    }

    public void añadirClientesAleatorios(){
        int numClientes = random.nextInt(3) + 1;
    
        for (int i = 0; i < numClientes; i++) {
            if (posicionUltimo < clientes.length) {
                clientes[posicionUltimo] = new Cliente();
                posicionUltimo++;
            }
        }
    }

    public void atenderCliente(){
        if(hayClientes()){
            moverIzquierda(0, posicionUltimo);
            posicionUltimo--;
        }
    }

    private boolean hayClientes() {
        return clientes[0] != null;
    }

    private void moverIzquierda(int indice, int elementosOcupados){
        assert indice >= 0 && indice < elementosOcupados;

        for (int i = indice; i < elementosOcupados - 1; i++) {
            clientes[i] = clientes[i + 1];
        }

        if (elementosOcupados > 0) {
            clientes[elementosOcupados - 1] = null;
        }
    }

    public void clienteSeVa(){
        assert posicionUltimo > 0;
        moverIzquierda(random.nextInt(posicionUltimo), posicionUltimo);
        posicionUltimo--;
    }

    public void recibirItemsExternos() {
        if(posicionUltimo > 0 && random.nextInt(100) < 20){ 
            int clienteSeleccionado = random.nextInt(posicionUltimo); 
            int itemsExtra = 2 + random.nextInt(4);
            clientes[clienteSeleccionado].añadirItems(itemsExtra);
        }
    }

    public int getPosicionUltimo(){
        return posicionUltimo;
    }

    public void moverDerecha(int posicion) {
        assert posicion >= 0 && posicion < clientes.length; 
        assert posicionUltimo < clientes.length;
        
        int inicioMovimiento = Math.min(posicionUltimo, clientes.length - 2);
        
        for (int i = inicioMovimiento; i >= posicion; i--) {
            clientes[i + 1] = clientes[i]; 
        }
        posicionUltimo++;
        clientes[posicion] = new Cliente();
    }
}
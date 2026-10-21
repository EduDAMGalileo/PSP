package ejercicios.cuaderno0;

public class Ej06 {
	public static void main(String[] args) throws Exception {
		for (String c : new String[] {"0", "3", "7"}) {
			ProcessBuilder pb = Lanzador.para("ejercicios.cuaderno0.Sale", c);
			pb.inheritIO();
			int codigo = pb.start().waitFor();
			String veredicto = (codigo == 0) ? "todo bien" : "algo falló";
			System.out.println("Padre: código " + codigo + " -> " + veredicto);
		}
	}
}

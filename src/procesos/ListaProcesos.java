package procesos;


import java.util.List;
import java.util.Optional;

//Código complejo, ya lo veremos en detalle
public class ListaProcesos {
    public static void main(String[] args) {
        ProcessHandle yo = ProcessHandle.current();
        Optional<ProcessHandle> padre = yo.parent();
        System.out.println("Mi PID: " + yo.pid());
        System.out.println("PID de mi padre: "
                + (padre.isPresent() ? padre.get().pid() : "?"));
        System.out.println("Procesadores: "
                + Runtime.getRuntime().availableProcessors());

        List<ProcessHandle> procesos =
                ProcessHandle.allProcesses().toList();
        System.out.printf("%7s %7s  %-10s %s%n",
                "PID", "PPID", "USUARIO", "ORDEN");
        for (ProcessHandle p : procesos) {
            ProcessHandle.Info info = p.info();
            Optional<ProcessHandle> pp = p.parent();
            System.out.printf("%7d %7s  %-10s %s%n",
                    p.pid(),
                    pp.isPresent() ? String.valueOf(pp.get().pid()) : "-",
                    info.user().orElse("?"),
                    info.command().orElse("(no disponible)"));
        }
        System.out.println(procesos.size() + " procesos");
    }
}

package pl.edu.pw.ee;

public class Wezel{
    Wezel lewy;
    Wezel prawy;
    int priorytet;
    char znak;

    public Wezel porownaj(Wezel porownywany){
        if(porownywany.priorytet > this.priorytet){
            return this;
        }else{
            return porownywany;
        }
    }
}

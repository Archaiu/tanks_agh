package org.example.common;

public class PhotoInfo
{
    private PhotoInfo(){}
    private static PhotoInfo instance;
    public static PhotoInfo getInstance()
    {
        if ( instance == null){instance = new PhotoInfo();}
        return instance;
    }

    private final String[] PHOTO_PATHS = {
            "/universities/agh.png",
            "/universities/papieski.png",
            "/universities/PK.png",
            "/universities/uek.png",
            "/universities/ujj.png",
            "/universities/ukenik.png",
            "/universities/up.png",
            "/universities/ur.png"
    };

    private  final String[] PHOTO_PATHS_SELECTED = {
            "/universities/agh_blue.png",
            "/universities/papieski_blue.png",
            "/universities/PK_blue.png",
            "/universities/uek_blue.png",
            "/universities/ujj_blue.png",
            "/universities/ukenik_blue.png",
            "/universities/up_blue.png",
            "/universities/ur_blue.png"
    };

    public String getPhoto(int i) {
        if (i >= 0 && i < PHOTO_PATHS.length) {
            return PHOTO_PATHS[i];
        } else {
            System.err.println("Błąd: Indeks zdjęcia poza zakresem w UserInfo.getPhoto(" + i + ")");
            return null;
        }
    }

    public String getPhotoSelected(int i) {
        if (i >= 0 && i < PHOTO_PATHS_SELECTED.length) {
            return PHOTO_PATHS_SELECTED[i];
        } else {
            System.err.println("Błąd: Indeks wybranego zdjęcia poza zakresem w UserInfo.getPhotoSelected(" + i + ")");
            return null;
        }
    }
}

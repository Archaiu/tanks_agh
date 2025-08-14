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
}

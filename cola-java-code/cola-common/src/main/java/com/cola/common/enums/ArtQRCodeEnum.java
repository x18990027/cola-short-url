package com.cola.common.enums;



import java.util.Map;

public class ArtQRCodeEnum {


    public enum typeEnum{

        LINK(1,"link","链接"),
        TEXT(2,"text","文本")
        ;
        private Integer type;
        private String typeCode;
        private String typeCodeCn;
        private static Map<Integer,String> typeMap;

        static {
            typeMap.put(LINK.type, LINK.typeCode);
            typeMap.put(TEXT.type, TEXT.typeCode);
        }

        private typeEnum(Integer type,String typeCode,String typeCodeCn){
            this.type=type;
            this.typeCode=typeCode;
            this.typeCodeCn=typeCodeCn;
        }

        public Integer getType() {
            return type;
        }

        public String getTypeCode() {
            return typeCode;
        }

        public static Map<Integer, String> getTypeMap() {
            return typeMap;
        }
    }




    //预设背景风格。二维码背景的风格
    public enum presetEnum{

        SUNSET(1,"sunset","日落"),
        FLORAL(2,"floral","花卉"),
        SNOWFLAKES(3,"snowflakes","雪花"),
        FEATHERS(4,"feathers","羽毛"),
        RAINDROPS(5,"raindrops","雨滴"),
        ULTRA_REALISM(6,"ultra-realism","超现实主义"),
        EPIC_REALMS(7,"epic-realms","王国史诗"),
        INTRICATE_STUDIO(8,"intricate-studio","复杂工作室"),
        SYMMETRIC_MASTERPIECE(9,"symmetric-masterpiece","极致对称"),
        LUMINOUS_HIGHWAY(10,"luminous-highway","霓虹公路"),
        CELESTIAL_JOURNEY(11,"celestial-journey","星际旅行"),
        NEON_MECH(12,"neon-mech","赛博朋克"),
        ETHEREAL_LOW_POLY(13,"ethereal-low-poly","空灵低聚"),
        GOLDEN_VISTA(14,"golden-vista","金色远景"),
        CINEMATIC_EXPANSE(15,"cinematic-expanse","电影般的广阔"),
        CINEMATIC_WARM(16,"cinematic-warm","电影版的温暖"),
        DESOLATE_WILDERNESS(17,"desolate-wilderness","荒野"),
        VIBRANT_PALETTE(18,"vibrant-palette","调色板"),
        ENIGMATIC_JOURNEY(19,"enigmatic-journey","神秘之旅"),
        TIMELESS_CINEMATIC(20,"timeless-cinematic","永恒的电影"),
        REGAL_GALAXY(21,"regal-galaxy","星系"),
        SERENE_HAZE(22,"serene-haze","雾霾"),
        ;

        private  Integer type;
        private  String code;
        private  String codeCn;

        private static Map<Integer,String> presetMap;

        static {
            presetMap.put(SUNSET.getType(),SUNSET.getCode());
            presetMap.put(FLORAL.getType(),FLORAL.getCode());
            presetMap.put(SNOWFLAKES.getType(),SNOWFLAKES.getCode());
            presetMap.put(FEATHERS.getType(),FEATHERS.getCode());
            presetMap.put(RAINDROPS.getType(),RAINDROPS.getCode());
            presetMap.put(ULTRA_REALISM.getType(),ULTRA_REALISM.getCode());
            presetMap.put(EPIC_REALMS.getType(),EPIC_REALMS.getCode());
            presetMap.put(INTRICATE_STUDIO.getType(),INTRICATE_STUDIO.getCode());
            presetMap.put(SYMMETRIC_MASTERPIECE.getType(),SYMMETRIC_MASTERPIECE.getCode());
            presetMap.put(LUMINOUS_HIGHWAY.getType(),LUMINOUS_HIGHWAY.getCode());
            presetMap.put(CELESTIAL_JOURNEY.getType(),CELESTIAL_JOURNEY.getCode());
            presetMap.put(NEON_MECH.getType(),NEON_MECH.getCode());
            presetMap.put(ETHEREAL_LOW_POLY.getType(),ETHEREAL_LOW_POLY.getCode());
            presetMap.put(GOLDEN_VISTA.getType(),GOLDEN_VISTA.getCode());
            presetMap.put(CINEMATIC_EXPANSE.getType(),CINEMATIC_EXPANSE.getCode());
            presetMap.put(CINEMATIC_WARM.getType(),CINEMATIC_WARM.getCode());
            presetMap.put(DESOLATE_WILDERNESS.getType(),DESOLATE_WILDERNESS.getCode());
            presetMap.put(VIBRANT_PALETTE.getType(),VIBRANT_PALETTE.getCode());
            presetMap.put(ENIGMATIC_JOURNEY.getType(),ENIGMATIC_JOURNEY.getCode());
            presetMap.put(TIMELESS_CINEMATIC.getType(),TIMELESS_CINEMATIC.getCode());
            presetMap.put(REGAL_GALAXY.getType(),REGAL_GALAXY.getCode());
            presetMap.put(SERENE_HAZE.getType(),SERENE_HAZE.getCode());
        }

        private presetEnum(Integer type,String code,String codeCn){
        this.type=type;
        this.code=code;
        this.codeCn=codeCn;
        }

        public Integer getType() {
            return type;
        }

        public String getCode() {
            return code;
        }

        public String getCodeCn() {
            return codeCn;
        }

        public static Map<Integer, String> getPresetMap() {
            return presetMap;
        }
    }





















}

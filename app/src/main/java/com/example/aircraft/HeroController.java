package com.example.aircraft;

import com.example.aircraft.air.HeroAircraft;

public class HeroController {
    private GameView game;
    private HeroAircraft heroAircraft;
//    private MouseAdapter mouseAdapter;

//    public HeroController(AbstractGame game, HeroAircraft heroAircraft){
//        this.game = game;
//        this.heroAircraft = heroAircraft;
//
//        mouseAdapter = new MouseAdapter() {
//            @Override
//            public void mouseDragged(MouseEvent e) {
//                super.mouseDragged(e);
//                int x = e.getX();
//                int y = e.getY();
//                if ( x<0 || x>Main.WINDOW_WIDTH || y<0 || y>Main.WINDOW_HEIGHT){
//                    // 防止超出边界
//                    return;
//                }
//                heroAircraft.setLocation(x, y);
//            }
//        };
//
//        game.addMouseListener(mouseAdapter);
//        game.addMouseMotionListener(mouseAdapter);
//    }


}

/*
Matteo's simple game
Started: February 2nd
*/

const tiles : int := 16;
const texWidth : int := 32;
const texes : int := 6;

var area : array 1..tiles, 1..tiles of int;

const tex_error : int := Pic.FileNew("res/tile/error.gif")

var window : int;

procedure resetArea
    for x : 1..tiles
        for y : 1..tiles
            area(x,y) := tex_error;
        end for
    end for
end resetArea

procedure setTile(x : int, y : int)
end setTile

procedure drawArea
    for x : 1..tiles
        for y : 1..tiles
            Pic.Draw(area(x,y),(x-1)*texWidth, (y-1)*texWidth, picMerge)
        end for
    end for
end drawArea

procedure openWindow
    window := Window.Open("graphics:"+intstr(texWidth*tiles)+";"+intstr(texWidth*tiles)+",offscreenonly")
end openWindow

procedure main
    openWindow
    loop
        drawArea
    end loop
end main

main


I = imread('свд1край2х.bmp');
%J=I;
J=rgb2gray(I)
%J = medfilt2(J);
%background=imopen(J, strel('square', 1));
%imshow(background);
% Отобразим поверхность фона
%figure, surf(double(background(1:1:end, 1:1:end))), zlim([0 255]);
%set(gca, 'ydir', 'reverse');
%I2=imsubtract(J, background);
figure,imshow(I2)
level1=graythresh(I);
BW = edge(J,'canny',level1);
BW2 = edge(J,'canny');
BW1 = edge(I2,'sobel');
figure,imshow(BW)
figure,imshow(BW1)
figure,imshow(BW2)
%fonnn=im2bw(I2,level1);
%figure,imshow(fonnn);
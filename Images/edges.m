I = imread('свд1край5.bmp');
J=rgb2gray(I);
BW1 = edge(J,'prewitt',0.15);
level1=graythresh(I);
%J1=im2bw(J,level);

%figure, imshow(I(1:1:100,1:1:100));
%figure, imshow(BW);
K = wiener2(J,[3 3]);
J = medfilt2(J);
BW = edge(J,'canny',level1);
BW = edge(J,'canny',level1-0.1);
figure, imshow(BW);
figure, imshow(BW1);
figure, imshow(J);
[n1 n2]=size(J);
A=zeros(n1,n2);
for n=25:5:50
%n=10;
J1=J;
for i=1:n:n1-n
    for j=1:n:n2-n
       %level=graythresh(J(i:1:i+n,j:1:j+n));
       J1(i:1:i+n,j:1:j+n)=255*edge(J(i:1:i+n,j:1:j+n),'canny',level);
    end
end
figure, imshow(J1);
end
figure, imshow(K);
figure, imshow(255*im2bw(J,level1));
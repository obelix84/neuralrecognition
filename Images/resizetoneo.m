%изменяетразмер прокрашенного изображения
%   size_new - новый размер
function BW1=resizetoneo(BW, size_new)
[n m]=size(BW);
maxx=max(n,m)
alfa=size_new/maxx
BW=imresize(BW,alfa,'bicubic')
figure, imshow(BW)
BW=im2bw(BW);
%BW=binarize(BW);
figure, imshow(BW)
BW=skeletonization(BW);
figure, imshow(BW)
BW1=BW;

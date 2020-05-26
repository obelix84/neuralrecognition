function testing_edge(imname)
I=imread(imname);
J=rgb2gray(I);
for i=1:1:6
    BW=edge(J,'canny',0.1*i);
    subplot(3,2,i),imshow(BW)
end
figure
for i=7:1:9
    BW=edge(J,'canny',0.1*i);
    subplot(3,2,i-6),imshow(BW)
end
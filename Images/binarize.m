function B=binarize(BW)
[m n]=size(BW);
for i=1:m
    for j=1:n
        if (BW(i,j)>=1)
            BW(i,j)=1;
        end
        if (BW(i,j)<1)
            BW(i,j)=0;   
        end
    end
end
B=BW;
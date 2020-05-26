function marking(I)
[n m] = size(I);
statistic=zeros(n,1);
for i=1:n
    c=0;
    for j=1:m
        if (I(i,j)==0);
            c=c+1;
        end
    end
    statistic(i)=c;
    if (c<0)
        I(i,:)=100;
    end
end
disp=zeros(n,1);
for i=1:n
    for j=1:m
        if (I(i,j)==0);
            disp(i)=disp(i)+(1-statistic(i)/n)^2;   
        end
    end
end
disp=disp./n;
figure,imshow(I);
maxxx=max(statistic/n)
t=graythresh(statistic/n)
c=maxxx*t
statistic=statistic/n;
for i=1:n
    if (statistic(i)<=c);
        statistic(i)=0;
    end
end
figure,plot(statistic>c);
A=conv(statistic,[-1 2 1]');
figure,plot(A);

%figure,plot(disp,n:-1:1);
srednee=0;
for i=1:n
    srednee=statistic(i)+srednee;
end
srednee=srednee/n;
%figure,plot(statistic-srednee,n:-1:1);
%figure,imshow(statistic./maxxx);
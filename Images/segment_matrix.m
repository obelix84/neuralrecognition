function [x y]=segment_matrix(fname)
I=imread(fname);
J=rgb2gray(I);
[n m]= size(A);
maxlen=max([m n]);

temp=J;
J=zeros([maxlen maxlen]);

for i=1:n
    for j=1:m
        J(i,j)=temp(i,j);
    end  
end
x1=0;
y1=0;
for k=1:maxlen
    A1=J(1:(maxlen-k),1:(maxlen-k));
    A2=J(k:maxlen,k:maxlen);
    c1=criteria(A1);
    c2=criteria(A2);
    if (c1>c2)
        J=A1;
    else
        J=A2;
    end
end
[x y]
function crit=criteria(A)
[n m]= size(A);
c=0;
for i=1:n
    for j=1:m
        if (A(i,j)=1)
            c++;
        end
    end  
end
crit=c;
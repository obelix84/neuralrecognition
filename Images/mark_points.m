function mark_points(A,X)
[n m]=size(X);
for i=1:m
    A(i,X(i))=250;
end
imshow(A);

